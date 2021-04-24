package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Quotable;

import java.util.Objects;
import java.util.Optional;

public final class Keyword implements Atom, Quotable {

    private final String path;
    private final String name;

    private Keyword(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public static Keyword of(String name) {
        int i = name.indexOf('/');
        if (i == -1 || name.equals("/")) {
            return new Keyword(null, name);
        } else {
            return new Keyword(name.substring(0, i), name.substring(i + 1));
        }
    }

    public static Keyword of(String path, String name) {
        return new Keyword(path, name);
    }

    public Keyword.Builder builder() {
        return new Builder();
    }

    public final class Builder {

        private String path;
        private String name;

        public Builder() {
            this.path = Keyword.this.path;
            this.name = Keyword.this.name;
        }

        public Keyword build() {
            return new Keyword(path, name);
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

    @Override
    public String name() {
        return name;
    }

    @Override
    public String print() {
        return ":" + (path == null ? name : path + "/" + name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword symbol = (Keyword) o;
        return Objects.equals(path, symbol.path) && name.equals(symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, name);
    }
}
