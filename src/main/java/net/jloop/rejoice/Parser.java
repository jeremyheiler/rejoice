package net.jloop.rejoice;

public final class Parser {

    private final Lexer lexer = new Lexer();

    public Atom parse(Input input) {
        Lexer.Token token = lexer.lex(input);
        switch (token.getType()) {
            case Bool -> {
                return new Bool(token.getLexeme().equals("true"));
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
