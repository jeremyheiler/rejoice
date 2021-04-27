package net.jloop.rejoice;

import net.jloop.rejoice.util.ReaderIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

// TODO(jeremy): Maybe move some of the logic up one level into the parser.
// For example, if the lexer returns a NewLine token, then the parser could return a symbol
// token for '//' and call a lexer function that will consume all characters until a new line
// is found. This same logic could be used for parsing strings and other 'delimited' lexemes.
// The advantage of doing this in the lexer is that there doesn't need to be extra whitespace
// around the tokens. That is, the string '"foo"' could be parsed as is, instead of '" foo"',
// as you would see in a forth-like language. This ia precursor to "parsing words".

public final class Lexer {
    private static final String whitespace = " \t\r\n";
    private static final String special = ";()[]{}";
    private static final String allow = "!$%&*+.,-/0123456789<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_`abcdefghijklmnopqrstuvwxyz|~";

    public Iterator<Token> map(ReaderIterator input) {
        class Mapper implements Iterator<Token>, Consumer<Token> {
            private final ArrayList<Token> tokens = new ArrayList<>();

            private State state = new StartingState();

            @Override
            public void accept(Token token) {
                tokens.add(token);
            }

            @Override
            public boolean hasNext() {
                while (true) {
                    if (!tokens.isEmpty()) {
                        return true;
                    }
                    if (state == null) {
                        return false;
                    }
                    if (input.hasNext()) {
                        state = state.consume(input.next(), this);
                    } else {
                        state.complete(this);
                        state = null;
                    }
                }
            }

            @Override
            public Token next() {
                if (hasNext()) {
                    return tokens.remove(0);
                } else {
                    throw new NoSuchElementException();
                }
            }
        }
        return new Mapper();
    }

    private interface State {
        State consume(char character, Consumer<Token> collector);

        void complete(Consumer<Token> collector);
    }

    private static final class StartingState implements State {

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if (character == '"') {
                return new StringState();
            } else if (character == '\'') {
                return new QuoteState();
            } else if (character == ':') {
                return new KeywordState();
            } else if (character == '#') {
                return new CommentState();
            } else if (character == '\\') {
                return new CharacterState();
            } else if (character == '^') {
                return new TypeState();
            } else if (character >= '0' && character <= '9') {
                return new IntegerState().consume(character, collector);
            } else if (contains(special, character)) {
                collector.accept(Token.of(Token.Type.Symbol, String.valueOf(character)));
                return new StartingState();
            } else if (contains(allow, character)) {
                return new SymbolState().consume(character, collector);
            } else if (contains(whitespace, character)) {
                return this;
            } else {
                throw new RuntimeError("LEX", "Unsupported character: '" + character + "'");
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            // There's nothing to do, as this state could only collect whitespace.
        }
    }

    private static final class NamedMetaState implements State {
        private final StringBuilder buffer;
        private final Token.Type type;

        private boolean first = true;
        private boolean slash = false;
        private boolean dot = false;

        public NamedMetaState(Token.Type type) {
            this(type, new StringBuilder());
        }

        public NamedMetaState(Token.Type type, StringBuilder buffer) {
            this.type = type;
            this.buffer = buffer;
        }

        // TODO: Symbols: Check for valid module names.
        // TODO: Match all printable characters.

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(special, character))) {
                complete(collector);
                return new StartingState().consume(character, collector);
            } else if (contains(allow, character)) {
                if (first) {
                    first = false;
                    if (character == '.') {
                        throw new RuntimeError("LEX", "Invalid " + type.name().toLowerCase() + ": cannot start with a '.' character");
                    }
                }
                if (character == '.') {
                    if (dot) {
                        throw new RuntimeError("LEX", "Invalid " + type.name().toLowerCase() + ": cannot have consecutive '.' characters");
                    } else {
                        dot = true;
                    }
                } else {
                    dot = false;
                }
                if (character == '/') {
                    if (slash) {
                        throw new RuntimeError("LEX", "Invalid " + type.name().toLowerCase() + ": cannot have more than one '/' character");
                    } else {
                        slash = true;
                    }
                }
                buffer.append(character);
                return this;
            } else {
                throw new RuntimeError("LEX", "Invalid " + type.name().toLowerCase() + ": disallowed character: '" + character + "'");
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            collector.accept(Token.of(type, buffer.toString()));
        }
    }

    private static final class SymbolState implements State {

        @Override
        public State consume(char character, Consumer<Token> collector) {
            return new NamedMetaState(Token.Type.Symbol).consume(character, collector);
        }

        @Override
        public void complete(Consumer<Token> collector) {
            // There's nothing to do, as this state immediately delegates to another.
        }
    }

    private static final class KeywordState implements State {
        private final StringBuilder buffer = new StringBuilder().append(':');

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(special, character))) {
                complete(collector);
                return new StartingState().consume(character, collector);
            } else {
                return new NamedMetaState(Token.Type.Keyword, buffer).consume(character, collector);
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            collector.accept(Token.of(Token.Type.Symbol, buffer.toString()));
        }
    }

    private static final class QuoteState implements State {
        private final StringBuilder buffer = new StringBuilder().append('\'');

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(special, character))) {
                throw new RuntimeError("LEX", "Incomplete quoted symbol");
            } else if (character == '\'') {
                buffer.append(character);
                return this;
            } else {
                return new NamedMetaState(Token.Type.Quote, buffer).consume(character, collector);
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            throw new RuntimeError("LEX", "Incomplete quoted symbol");
        }
    }

    private static final class IntegerState implements State {
        private final StringBuilder buffer = new StringBuilder();

        // TODO: Support negative integers
        // TODO: What should be done with leading zeros?

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if (character >= '0' && character <= '9') {
                buffer.append(character);
                return this;
            } else {
                complete(collector);
                return new StartingState().consume(character, collector);
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            collector.accept(Token.of(Token.Type.Int, buffer.toString()));
        }
    }

    private static final class StringState implements State {
        private final StringBuilder buffer = new StringBuilder();

        // TODO: Support escape characters.

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if (character == '"') {
                collector.accept(Token.of(Token.Type.Str, buffer.toString()));
                return new StartingState();
            } else {
                buffer.append(character);
                return this;
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            throw new RuntimeError("LEX", "Run-on string");
        }
    }

    private static final class CommentState implements State {
        private final StringBuilder buffer = new StringBuilder();

        private boolean foundReturn = false;

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if (character == '\n') {
                complete(collector);
                return new StartingState();
            } else if (character == '\r') {
                if (foundReturn) {
                    buffer.append('\r');
                }
            } else {
                foundReturn = false;
                buffer.append(character);
            }
            return this;
        }

        @Override
        public void complete(Consumer<Token> collector) {
            collector.accept(Lexer.Token.of(Lexer.Token.Type.Comment, buffer.toString()));
        }
    }

    private static final class CharacterState implements State {
        private final StringBuilder buffer = new StringBuilder().append('\\');

        // TODO: Validate characters as they're being consumed.
        // TODO: Match all printable characters when parsing literals.
        // TODO: Maybe restrict which single characters can be escaped?

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(special, character))) {
                complete(collector);
                return new StartingState().consume(character, collector);
            } else {
                buffer.append(character);
                return this;
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            String lexeme = buffer.toString();
            if (lexeme.substring(1).matches("^(?:u[A-Fa-f0-9]{4}|[\\\\0-9]+|\\\\[a-z]|[A-Za-z]+)$")) {
                collector.accept(Token.of(Token.Type.Character, buffer.toString()));
            } else {
                throw new RuntimeError("LEX", "Invalid character literal: " + lexeme);
            }
        }
    }

    private static final class TypeState implements State {
        private final StringBuilder buffer = new StringBuilder().append('^');

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(special, character))) {
                complete(collector);
                return new StartingState().consume(character, collector);

            } else {
                buffer.append(character);
                return this;
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            if (buffer.length() == 1) {
                throw new RuntimeError("LEX", "Incomplete type");
            } else {
                collector.accept(Token.of(Token.Type.Type, buffer.toString()));
            }
        }
    }

    private static boolean contains(String s, int c) {
        return s.indexOf(c) != -1;
    }

    public static final class Token {

        private final Type type;
        private final String lexeme;

        // TODO: Store source information like line, column, and file.

        public Token(Type type, String lexeme) {
            this.type = type;
            this.lexeme = lexeme;
        }

        public static Token of(Type type) {
            return new Token(type, null);
        }

        public static Token of(Type type, String lexeme) {
            return new Token(type, lexeme);
        }

        public Type type() {
            return type;
        }

        public String lexeme() {
            return lexeme;
        }

        public enum Type {
            Bool,
            Character,
            Comment,
            Int,
            Keyword,
            Quote,
            Str,
            Symbol,
            Type
        }
    }
}
