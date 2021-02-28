package net.jloop.rejoice;

import java.util.Objects;
import java.util.Optional;

public class Symbol implements Literal {

    private final String value;

    public Symbol(String value) {
        this.value = value;
    }

    @Override
    public Stack evaluate(Library library, Stack stack) {
        Optional<Operator> operator = library.lookup(this);
        if (operator.isPresent()) {
            return operator.get().evaluate(library, stack);
        }
        throw new RuntimeException("Could not find operator for symbol " + value);
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
