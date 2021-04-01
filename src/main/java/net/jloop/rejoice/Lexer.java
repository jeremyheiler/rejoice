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

    // TODO(jeremy): Store rules in a map, keyed on the dispatch character.
    private final LexerRule comment;

    public Lexer(LexerRule comment) {
        this.comment = comment;
    }

    public Iterable<Token> lex(Input input) {
        ArrayList<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = next(input)).type != Token.Type.EOF) {
            tokens.add(token);
        }
        return tokens;
    }

    private Token next(Input input) {
        PushbackReader reader = input.getReader();
        try {
            int c;

            // Consume whitespace
            while ((c = reader.read()) != EOF) {
                if (!(c == ' ' || c == '\t' || c == '\n')) {
                    reader.unread(c);
                    break;
                }
            }
            if (c == EOF) {
                return Token.of(Token.Type.EOF);
            }

            // Consume the next token
            if ((c = reader.read()) != EOF) {
                if (c == '"') {
                    // Parse a string
                    StringBuilder buf = new StringBuilder();
                    while ((c = reader.read()) != EOF) {
                        if (c == '"') {
                            return Token.of(Token.Type.Str, buf.toString());
                        } else {
                            buf.append((char) c);
                        }
                    }
                    // The loop ended on EOF without closing the string
                    throw new RuntimeError("LEX", "Unexpected EOF: Run-on string");
                } else if (c == '[') {
                    return Token.of(Token.Type.Symbol, "[");
                } else if (c == ']') {
                    return Token.of(Token.Type.Symbol, "]");
                } else if ( c == ';') {
                    return Token.of(Token.Type.Symbol, ";");
                } else if ( c == '.') {
                    return Token.of(Token.Type.Symbol, ".");
                } else if (c == comment.dispatcher()) {
                    return comment.lex(reader);
                } else {
                    // Parse a literal
                    if (c >= '!' && c <= 'z') { // Valid characters except for '[', ']', ';', and '.' which are filtered out above
                        StringBuilder buf = new StringBuilder().append((char) c);
                        int d;
                        while ((d = reader.read()) != EOF) {
                            if (d >= '!' && d <= 'z' && d != '[' && d != ']' && d != ';' && d != '.') {
                                buf.append((char) d);
                            } else {
                                reader.unread(d);
                                break;
                            }
                        }
                        String value = buf.toString();
                        if (value.matches("-?\\d+")) {
                            return Token.of(Token.Type.Int, value);
                        } else if (value.equals("true") || value.equals("false")) {
                            // TODO(jeremy): Replace boolean literals with operators?
                            return Token.of(Token.Type.Bool, value);
                        } else {
                            return Token.of(Token.Type.Symbol, value);
                        }
                    } else {
                        throw new RuntimeError("LEX", "Unknown character '" + c + '"');
                    }
                }
            } else {
                return Token.of(Token.Type.EOF);
            }
        } catch (IOException cause) {
            throw new RuntimeError("LEX", cause);
        }
    }

    public static final class Token {

        private final Type type;
        private final String lexeme;

        // TODO(jeremy) Store source information like line, column, and file

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
            EOF,
            Int,
            LineComment,
            Str,
            Symbol
        }
    }
}
