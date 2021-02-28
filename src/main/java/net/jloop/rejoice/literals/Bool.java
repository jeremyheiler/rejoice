package net.jloop.rejoice.literals;

import net.jloop.rejoice.Literal;

public class Bool implements Literal {

    private final boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
