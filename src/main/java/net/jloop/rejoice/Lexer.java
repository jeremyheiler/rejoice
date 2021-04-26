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
    private static final String adjacent = ".;()[]{}";
    private static final String allow = "!$%&*+,-/0123456789<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_`abcdefghijklmnopqrstuvwxyz|~:";

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

        default void complete(Consumer<Token> collector) {
            // Do nothing
        }
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
            } else if (contains(adjacent, character)) {
                collector.accept(Token.of(Token.Type.Symbol, String.valueOf(character)));
                return new StartingState();
            } else if (contains(allow, character)) {
                return new SymbolOrIntegerState().consume(character, collector);
            } else {
                return this;
            }
        }
    }

    private static final class SymbolOrIntegerState implements State {
        private final StringBuilder buffer = new StringBuilder();

        // TODO: Match all printable characters.
        // TODO: Symbols: Check for valid module names.
        // TODO: Symbols: Check for multiple forward slashes.
        // TODO: Symbols: Check for consecutive dots.

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(adjacent, character))) {
                complete(collector);
                return new StartingState().consume(character, collector);
            } else if (contains(allow, character)) {
                buffer.append(character);
                return this;
            } else {
                throw new RuntimeError("LEX", "Invalid symbol character: '" + character + "'");
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            String lexeme = buffer.toString();
            if (lexeme.matches("-?\\d+")) {
                collector.accept(Token.of(Token.Type.Int, lexeme));
            } else if (lexeme.equals("true") || lexeme.equals("false")) {
                collector.accept(Token.of(Token.Type.Bool, lexeme));
            } else {
                collector.accept(Token.of(Token.Type.Symbol, lexeme));
            }
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

    private static final class KeywordState implements State {
        private final StringBuilder buffer = new StringBuilder().append(':');

        // TODO: Ensure this matches symbols exactly.

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(adjacent, character))) {
                complete(collector);
                return new StartingState().consume(character, collector);
            } else if (character == ':') {
                throw new RuntimeError("LEX", "The colon character cannot be within a keyword");
            } else {
                buffer.append(character);
                return this;
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            String lexeme = buffer.toString();
            if (lexeme.equals(":")) {
                collector.accept(Token.of(Token.Type.Symbol, lexeme));
            } else {
                collector.accept(Token.of(Token.Type.Keyword, lexeme));
            }
        }
    }

    private static final class QuoteState implements State {
        private final StringBuilder buffer = new StringBuilder().append('\'');

        private boolean doneQuoting = false;

        // TODO: Ensure this matches symbols exactly.

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if ((contains(whitespace, character) || contains(adjacent, character))) {
                complete(collector);
                return new StartingState().consume(character, collector);
            } else if (character == '\'') {
                if (doneQuoting) {
                    throw new RuntimeError("LEX", "The quote character cannot be within a symbol");
                } else {
                    buffer.append(character);
                    return this;
                }
            } else {
                doneQuoting = true;
                buffer.append(character);
                return this;
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            collector.accept(Token.of(Token.Type.Quote, buffer.toString()));
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
            if ((contains(whitespace, character) || contains(adjacent, character))) {
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
            if ((contains(whitespace, character) || contains(adjacent, character))) {
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
