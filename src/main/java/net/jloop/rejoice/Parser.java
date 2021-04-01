package net.jloop.rejoice;

import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;

public final class Parser {

    public Iterable<Atom> parse(Iterable<Lexer.Token> tokens) {
        ArrayList<Atom> atoms = new ArrayList<>();
        for (Lexer.Token token : tokens) {
            if (token != null) {
                if (token.type() == Lexer.Token.Type.EOF) {
                    break;
                } else {
                    atoms.add(translate(token));
                }
            }
        }
        return atoms;
    }

    private Atom translate(Lexer.Token token) {
        switch (token.type()) {
            case Bool -> {
                return Bool.of(token.lexeme());
            }
            case Int -> {
                return new Int64(Long.parseLong(token.lexeme()));
            }
            case LineComment -> {
                return null;
            }
            case Str -> {
                return new Str(token.lexeme());
            }
            case Symbol -> {
                return Symbol.of(token.lexeme());
            }
            default -> throw new IllegalStateException("Unexpected token of type: " + token.type());
        }
    }
}
