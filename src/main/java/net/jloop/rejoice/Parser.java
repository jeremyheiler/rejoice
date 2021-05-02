package net.jloop.rejoice;

import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Char;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Keyword;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.Iterator;

public final class Parser {

    public Iterator<Value> map(Iterator<Lexer.Token> input) {
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return input.hasNext();
            }

            @Override
            public Atom next() {
                Lexer.Token next = input.next();
                return translate(next);
            }
        };
    }

    private Atom translate(Lexer.Token token) {
        switch (token.type()) {
            case Bool -> {
                return Bool.of(token.lexeme());
            }
            case Character -> {
                return Char.of(token.lexeme());
            }
            case Int -> {
                return new Int64(Long.parseLong(token.lexeme()));
            }
            case Keyword -> {
                return Keyword.of(token.lexeme().substring(1));
            }
            case Quote -> {
                int index = token.lexeme().lastIndexOf('\'') + 1;
                String name = token.lexeme().substring(index);
                Quotable quotable = Symbol.of(name);
                for (int i = 0; i < index; i++) {
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
