package net.jloop.rejoice.literals;

import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Library;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Atom;

import java.util.Objects;

public class Symbol implements Atom {

    private final String value;

    public Symbol(String value) {
        this.value = value;
    }

    @Override
    public Stack evaluate(Library library, Stack stack) {
        Operator operator = library.lookup(this);
        if (operator == null) {
            throw new RuntimeException("No function named: " + value);
        }
        return operator.evaluate(library, stack);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(value, symbol.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
