package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public class Char implements Atom {

    private final String literal;
    private final char value;

    public Char(String literal, char value) {
        this.literal = literal;
        this.value = value;
    }

    public char get() {
        return value;
    }

    @Override
    public String print() {
        return literal;
    }
}
