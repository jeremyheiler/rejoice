package net.jloop.rejoice;

import java.util.Objects;

public class Int64 implements Literal {

    private final long value;

    public Int64(long value) {
        this.value = value;
    }

    public Int64 abs() {
        if (value < 0) {
            return new Int64(-value);
        } else {
            return this;
        }
    }

    public Int64 plus(Int64 that) {
        return new Int64(this.value + that.value);
    }

    public Int64 minus(Int64 that) {
        return new Int64(this.value - that.value);
    }

    public Int64 multiply(Int64 that) {
        return new Int64(this.value * that.value);
    }

    public Int64 divide(Int64 that) {
        return new Int64(this.value / that.value);
    }

    public Int64 mod(Int64 that) {
        return new Int64(this.value % that.value);
    }

    public Int64 max(Int64 that) {
        return new Int64(Long.max(this.value, that.value));
    }

    public Int64 min(Int64 that) {
        return new Int64(Long.min(this.value, that.value));
    }

    public Int64 signum() {
        if (value == 0) {
            return this;
        }else if (value > 0) {
            return new Int64(1);
        } else {
            return new Int64(-1);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Int64 int64 = (Int64) o;
        return value == int64.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
