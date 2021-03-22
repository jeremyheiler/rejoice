package net.jloop.rejoice;

import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;

public final class Input {

    private final PushbackReader reader;

    public Input(String program) {
        this.reader = new PushbackReader(new StringReader(program));
    }

    public Input(Reader reader) {
        if (reader instanceof PushbackReader) {
            this.reader = (PushbackReader) reader;
        } else {
            this.reader = new PushbackReader(reader);
        }
    }

    public PushbackReader getReader() {
        return reader;
    }
}
