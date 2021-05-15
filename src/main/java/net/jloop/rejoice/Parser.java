package net.jloop.rejoice;

import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.Char;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Keyword;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.ArrayDeque;
import java.util.Deque;

public final class Parser {

    public Deque<Value> parse(Deque<Lexer.Token> input) {
        Deque<Value> values = new ArrayDeque<>();
        for (Lexer.Token token : input) values.add(translate(token));
        return values;
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
                return Keyword.of(token.lexeme());
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
