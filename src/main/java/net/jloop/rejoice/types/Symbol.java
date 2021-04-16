package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.RuntimeError;

import java.util.Objects;
import java.util.Optional;

public final class Symbol implements Atom {

    private final String path;
    private final String name;

    private int quotes;

    private Symbol(String path, String name, int quotes) {
        this.path = path;
        this.name = name;
        this.quotes = quotes;
    }

    public static Symbol of(String name) {
        int i = name.indexOf('/');
        if (i == -1 || name.equals("/")) {
            return new Symbol(null, name, 0);
        } else {
            return new Symbol(name.substring(0, i), name.substring(i + 1), 0);
        }
    }

    public static Symbol of(String path, String name) {
        return new Symbol(path, name, 0);
    }

    public Symbol.Builder builder() {
        return new Builder();
    }

    public final class Builder {

        private String path;
        private String name;
        private int quotes;

        public Builder() {
            this.path = Symbol.this.path;
            this.name = Symbol.this.name;
            this.quotes = Symbol.this.quotes;
        }

        public Symbol build() {
            return new Symbol(path, name, quotes);
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }
    }

    public Optional<String> path() {
        return Optional.ofNullable(path);
    }

    public String name() {
        return name;
    }

    public boolean isQuoted() {
        return quotes > 0;
    }

    public Symbol quote() {
        ++quotes;
        return this;
    }

    public Symbol unquote() {
        if (quotes == 0) {
            throw new RuntimeError("INTERPRET", "Cannot unquote an unquoted symbol");
        }
        --quotes;
        return this;
    }

    @Override
    public String print() {
        return "'".repeat(quotes) + value();
    }

    @Override
    public String value() {
        return path == null ? name : path + "/" + name;
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
