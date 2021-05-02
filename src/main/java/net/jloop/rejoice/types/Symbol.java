package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Invocation;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Quotable;
import net.jloop.rejoice.Value;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Symbol implements Atom, Quotable {

    private final String path;
    private final String name;

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

    public static Symbol of(String path, String name) {
        return new Symbol(path, name);
    }

    public Symbol.Builder builder() {
        return new Builder();
    }

    public final class Builder {

        private String path;
        private String name;

        public Builder() {
            this.path = Symbol.this.path;
            this.name = Symbol.this.name;
        }

        public Symbol build() {
            return new Symbol(path, name);
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

    @Override
    public void collect(Env env, Iterator<Value> input, List<Value> collection) {
        Invocation invocation = env.resolve(this);
        if (invocation.invocable() instanceof Macro) {
            Iterator<Value> macro = ((Macro) invocation.invocable()).rewrite(env, input);
            while (macro.hasNext()) {
                collection.add(macro.next());
            }
        } else {
            collection.add(this);
        }
    }

    @Override
    public Stack interpret(Env env, Stack stack, Iterator<Value> input) {
        return env.resolve(this).invoke(env, stack, input);
    }

    public Optional<String> path() {
        return Optional.ofNullable(path);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Atom quote() {
        return new Quote(this);
    }

    @Override
    public Atom unquote(Env env) {
        if (path == null) {
            return builder().withPath(env.active().name()).build();
        } else {
            return this;
        }
    }

    @Override
    public String print() {
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
