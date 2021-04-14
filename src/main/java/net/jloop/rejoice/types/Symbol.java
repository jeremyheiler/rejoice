package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.RuntimeError;

import java.util.Objects;
import java.util.Optional;

public final class Symbol implements Atom {

    private final String path;
    private final String name;

    private int quote = 0;

    private Symbol(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public static Symbol of(String name) {
        int i = name.indexOf('/');
        if (i == -1 || name.equals("/")) {
            return new Symbol(null, name);
        } else {
            return new Symbol(name.substring(0, i), name.substring(i + 1));
        }
    }

    public Optional<String> path() {
        return Optional.ofNullable(path);
    }

    public String name() {
        return name;
    }

    public boolean isQuoted() {
        return quote > 0;
    }

    public Symbol quote() {
        ++quote;
        return this;
    }

    public Symbol unquote() {
        if (quote == 0) {
            throw new RuntimeError("INTERPRET", "Cannot unquote an unquoted symbol");
        }
        --quote;
        return this;
    }

    @Override
    public String print() {
        return value();
    }

    @Override
    public String value() {
        if (path == null) {
            return name;
        } else {
            return path + "/" + name;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(path, symbol.path) && name.equals(symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, name);
    }
}
