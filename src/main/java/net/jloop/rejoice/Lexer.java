package net.jloop.rejoice;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public final class Lexer {

    private static final String whitespace = "\t\n\r ";
    private static final String allowedInName = "!$%&()*+,-./0123456789;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]_`abcdefghijklmnopqrstuvwxyz{|}~";

    public Deque<Token> lex(Reader reader) {
        Deque<Token> tokens = new ArrayDeque<>();
        ReaderIterator input = new ReaderIterator(reader);
        State state = new StartingState();
        while (input.hasNext()) state = state.consume(input.next(), tokens::add);
        state.complete(tokens::add);
        return tokens;
    }

    private static final class ReaderIterator implements Iterator<Character> {

        private final Reader reader;

        private boolean advanced;
        private int next;

        public ReaderIterator(Reader reader) {
            this.reader = Objects.requireNonNull(reader);
        }

        private void advance() {
            try {
                next = reader.read();
            } catch (IOException e) {
                Exceptions.sneakyThrow(e);
            }
        }

        @Override
        public boolean hasNext() {
            if (!advanced) {
                advanced = true;
                advance();
            }
            return next != -1;
        }

        @Override
        public Character next() {
            if (advanced) {
                advanced = false;
            } else {
                advance();
            }
            if (next == -1) {
                throw new NoSuchElementException();
            } else {
                return (char) next;
            }
        }
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
            } else if (contains(allowedInName, character)) {
                return new SymbolOrBoolState().consume(character, collector);
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
            if (contains(whitespace, character)) {
                complete(collector);
                return new StartingState().consume(character, collector);
            } else if (contains(allowedInName, character)) {
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
                buffer.append(character);
                return this;
            } else {
                throw new RuntimeError("LEX", "Invalid " + type.name().toLowerCase() + ": disallowed character: '" + character + "'");
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            String lexeme = buffer.toString();
            if (type == Token.Type.Symbol && (lexeme.equals("true") || lexeme.equals("false"))) {
                collector.accept(Token.of(Token.Type.Bool, lexeme));
            } else if (buffer.charAt(buffer.length() - 1) == '.') {
                throw new RuntimeError("LEX", "Invalid " + type.name().toLowerCase() + ": cannot end with a '.' character");
            } else {
                collector.accept(Token.of(type, lexeme));
            }
        }
    }

    private static final class SymbolOrBoolState implements State {

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
            if (contains(whitespace, character)) {
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

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if (character == '\n') {
                return new StartingState();
            } else {
                return this;
            }
        }

        @Override
        public void complete(Consumer<Token> collector) {
            // There's nothing to do, as this state always delegates to another.
        }
    }

    private static final class CharacterState implements State {

        private final StringBuilder buffer = new StringBuilder().append('\\');

        // TODO: Validate characters as they're being consumed.
        // TODO: Match all printable characters when parsing literals.
        // TODO: Maybe restrict which single characters can be escaped?

        @Override
        public State consume(char character, Consumer<Token> collector) {
            if (contains(whitespace, character)) {
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
            if (contains(whitespace, character)) {
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
            Int,
            Keyword,
            Str,
            Symbol,
            Type
        }
    }
}
