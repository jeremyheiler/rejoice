package net.jloop.rejoice;

import java.io.IOException;
import java.io.PushbackReader;

public interface LexerRule {

    int dispatcher();

    Lexer.Token lex(PushbackReader reader) throws IOException;
}
