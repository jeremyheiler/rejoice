package net.jloop.rejoice;

import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;

public final class Parser {

    private final Lexer lexer = new Lexer();

    public Atom parse(Input input) {
        Lexer.Token token = lexer.lex(input);
        switch (token.getType()) {
            case Bool -> {
                return Bool.of(token.getLexeme());
            }
            case EOF -> {
                return null;
            }
            case Int -> {
                return new Int64(Long.parseLong(token.getLexeme()));
            }
            case Str -> {
                return new Str(token.getLexeme());
            }
            case Symbol -> {
                return Symbol.of(token.getLexeme());
            }
            default -> throw new RuntimeError("PARSE", "Unexpected token type: " + token.getType());
        }
    }
}
