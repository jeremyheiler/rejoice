package net.jloop.rejoice;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;

// TODO(jeremy): Maybe move some of the logic up one level into the parser.
// For example, if the lexer returns a NewLine token, then the parser could return a symbol
// token for '//' and call a lexer function that will consume all characters until a new line
// is found. This same logic could be used for parsing strings and other 'delimited' lexemes.
// The advantage of doing this in the lexer is that there doesn't need to be extra whitespace
// around the tokens. That is, the string '"foo"' could be parsed as is, instead of '" foo"',
// as you would see in a forth-like language. This ia precursor to "parsing words".

public final class Lexer {

    public static final int EOF = -1;

    private static final String whitespace = " \t\r\n";
    private static final String adjacent = ".:;[]{}";
    private static final String allow = "!$%&()*+,-/0123456789<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ^_`abcdefghijklmnopqrstuvwxyz|~";

    private boolean contains(String s, int c) {
        return s.indexOf(c) != -1;
    }

    public Iterable<Token> lex(Input input) {
        ArrayList<Token> tokens = new ArrayList<>();
        Token token = null;
        while ((token = next(input, token)).type != Token.Type.EOF) {
            tokens.add(token);
        }
        return tokens;
    }

    private Token next(Input input, Token previous) {
        PushbackReader reader = input.getReader();
        try {
            int c;
            boolean consumedWhitespace = false;

            // Consume any whitespace
            while (true) {
                if ((c = reader.read()) == EOF) {
                    return Token.of(Token.Type.EOF);
                } else if (contains(whitespace, c)) {
                    consumedWhitespace = true;
                } else {
                    break;
                }
            }

            // Maybe consume a token that can be adjacent to others
            if (contains(adjacent, c)) {
                return Token.of(Token.Type.Symbol, String.valueOf((char) c), true);
            }

            if (!consumedWhitespace && previous != null && !previous.isAdjacent()) {
                throw new RuntimeError("LEX", "Refusing to parse two non-adjacent tokens without any whitespace between them");
            }

            // Consume the next token
            if (c == '"') {
                // Parse a string
                // TODO: Parse escaped characters
                StringBuilder buffer = new StringBuilder();
                while (true) {
                    int d = reader.read();
                    if (d == EOF) {
                        throw new RuntimeError("LEX", "Unexpected EOF: Run-on string literal");
                    }
                    if (d == '"') {
                        return Token.of(Token.Type.Str, buffer.toString());
                    }
                    buffer.append((char) d);
                }
            } else if (c == '#') {
                StringBuilder buffer = new StringBuilder();
                while (true) {
                    int d = reader.read();
                    if (d == EOF || d == '\n') {
                        return Lexer.Token.of(Lexer.Token.Type.LineComment, buffer.toString(), true);
                    }
                    if (d == '\r') {
                        int e;
                        if ((e = reader.read()) == EOF || e == '\n') {
                            return Lexer.Token.of(Lexer.Token.Type.LineComment, buffer.toString(), true);
                        }
                        reader.unread(e);
                    }
                    buffer.append((char) d);
                }
            } else if (c == '\\') {
                StringBuilder buffer = new StringBuilder().append((char) c);
                while (true) {
                    int d = reader.read();
                    if (d == EOF) {
                        break;
                    } else if (contains(whitespace, d)) {
                        reader.unread(d);
                        break;
                    }
                    buffer.append((char) d);
                }
                String lexeme = buffer.toString();
                // TODO(jeremy): Match all printable characters in the last case, not just alphanumerics.
                // TODO(jeremy): Be more restrictive in which single characters can be escaped?
                if (lexeme.substring(1).matches("^(?:u[A-Fa-f0-9]{4}|[\\\\0-9]+|\\\\[a-z]|[A-Za-z]+)$")) {
                    return Token.of(Token.Type.Character, buffer.toString());
                } else {
                    throw new RuntimeError("LEX", "Invalid character literal: " + lexeme);
                }
            } else if (c == '\'') {
                // TODO: Accept multiple leading single quotes
                StringBuilder buffer = new StringBuilder().append("'");
                while (true) {
                    int d = reader.read();
                    if (d == EOF) {
                        break;
                    } else if (contains(whitespace, d) || contains(adjacent, d)){
                        reader.unread(d);
                        break;
                    } else if (contains(allow, d)) {
                        buffer.append((char) d);
                    } else {
                        throw new RuntimeError("LEX", "Invalid symbol character: '" + (char) d + "'");
                    }
                }
                if (buffer.length() == 1) {
                    throw new RuntimeError("LEX", "Unexpected end of symbol");
                } else {
                    return Token.of(Token.Type.Symbol, buffer.toString());
                }
            } else if (contains(allow, c)) {
                // Parse a literal
                StringBuilder buffer = new StringBuilder().append((char) c);
                int d;
                while ((d = reader.read()) != EOF) {
                    if (contains(allow, d)) {
                        buffer.append((char) d);
                    } else if (contains(whitespace, d) || contains(adjacent, d)){
                        reader.unread(d);
                        break;
                    } else {
                        throw new RuntimeError("LEX", "Invalid symbol character: '" + (char) d + "'");
                    }
                }
                String lexeme = buffer.toString();
                if (lexeme.matches("-?\\d+")) {
                    return Token.of(Token.Type.Int, lexeme);
                } else if (lexeme.equals("true") || lexeme.equals("false")) {
                    return Token.of(Token.Type.Bool, lexeme);
                } else {
                    return Token.of(Token.Type.Symbol, lexeme);
                }
            } else {
                throw new RuntimeError("LEX", "Unsupported character '" + (char) c + "'");
            }
        } catch (IOException cause) {
            throw new RuntimeError("LEX", cause);
        }
    }

    public static final class Token {

        private final Type type;
        private final String lexeme;
        private final boolean adjacent;

        // TODO(jeremy) Store source information like line, column, and file

        public Token(Type type, String lexeme, boolean adjacent) {
            this.type = type;
            this.lexeme = lexeme;
            this.adjacent = adjacent;
        }

        public static Token of(Type type) {
            return new Token(type, null, false);
        }

        public static Token of(Type type, String lexeme) {
            return new Token(type, lexeme, false);
        }

        public static Token of(Type type, String lexeme, boolean adjacent) {
            return new Token(type, lexeme, adjacent);
        }

        public Type type() {
            return type;
        }

        public String lexeme() {
            return lexeme;
        }

        public boolean isAdjacent() {
            return adjacent;
        }

        public enum Type {
            Bool,
            Character,
            EOF,
            Int,
            LineComment,
            Str,
            Symbol
        }
    }
}
