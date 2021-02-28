package net.jloop.rejoice;

import java.util.Objects;

public class Bool implements Literal {

    private final boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    public boolean isTrue() {
        return value;
    }

    public boolean isFalse() {
        return !value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bool bool = (Bool) o;
        return value == bool.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
