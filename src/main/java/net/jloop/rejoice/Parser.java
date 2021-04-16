package net.jloop.rejoice;

import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Char;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.ArrayList;

public final class Parser {

    public Iterable<Atom> parse(Iterable<Lexer.Token> tokens) {
        ArrayList<Atom> atoms = new ArrayList<>();
        for (Lexer.Token token : tokens) {
            if (token.type() == Lexer.Token.Type.EOF) {
                break;
            } else {
                Atom atom = translate(token);
                if (atom != null) {
                    atoms.add(atom);
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
            case Character -> {
                return Char.of(token.lexeme());
            }
            case Comment -> {
                return null;
            }
            case Int -> {
                return new Int64(Long.parseLong(token.lexeme()));
            }
            case Quote -> {
                int symbolStart = token.lexeme().lastIndexOf('\'') + 1;
                String name = token.lexeme().substring(symbolStart);
                Quotable quotable = Symbol.of(name);
                for (int i = 0; i < symbolStart; i++) {
                    quotable = new Quote(quotable);
                }
                return quotable;
            }
            case Str -> {
                return new Str(token.lexeme());
            }
            case Symbol -> {
                return Symbol.of(token.lexeme());
            }
            case Type -> {
                return Type.of(token.lexeme().substring(1));
            }
            default -> throw new IllegalStateException("Unexpected token of type: " + token.type());
        }
    }
}
