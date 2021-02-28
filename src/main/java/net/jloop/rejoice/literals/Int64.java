package net.jloop.rejoice.literals;

import net.jloop.rejoice.Literal;

public class Int64 implements Literal {

    private final long value;

    public Int64(long value) {
        this.value = value;
    }

    public Int64 plus(Int64 that) {
        return new Int64(this.value + that.value);
    }

    public Int64 minus(Int64 that) {
        return new Int64(this.value - that.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
