package net.jloop.rejoice;

import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;

import java.util.Iterator;
import java.util.Objects;

public final class Parser {

    public Iterable<Atom> iterable(Iterator<Lexer.Token> tokens) {
        return () -> new Iterator<>() {

            @Override
            public boolean hasNext() {
                return tokens.hasNext();
            }

            @Override
            public Atom next() {
                Atom atom = parse(tokens.next());
                // Recursively parse tokens until one is found
                return Objects.requireNonNullElseGet(atom, this::next);
            }
        };
    }

    public Atom parse(Lexer.Token token) {
        System.out.println(token.type() + " " + token.lexeme());
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
