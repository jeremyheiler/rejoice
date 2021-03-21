package net.jloop.rejoice;

import java.util.Objects;

public class Symbol implements Literal {

    private final String value;

    public Symbol(String value) {
        this.value = value;
    }

    @Override
    public Stack accept(Stack stack, Interpreter interpreter) {
        return interpreter.evaluate(stack, this);
    }

    public String getName() {
        return value;
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
