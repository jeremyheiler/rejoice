package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.ConcatIterator;
import net.jloop.rejoice.Context;
import net.jloop.rejoice.Module;
import net.jloop.rejoice.Quotable;
import net.jloop.rejoice.Stack;

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
    public Iterator<Atom> rewrite(Context context, List<Atom> output, Iterator<Atom> input) {
        if (context.macros().containsKey(name)) {
            // A rewritten macro could include other macros. In order to process them
            // the rewritten stream is concatenated with the remaining input stream.
            Iterator<Atom> rewritten = context.macros().get(name).rewrite(context, input);
            return new ConcatIterator<>(rewritten, input);
        } else {
            output.add(this);
            return input;
        }
    }

    @Override
    public Stack interpret(Stack stack, Context context) {
        Module.Resolved resolved = context.resolve(this);
        context.trace().add(resolved.toCall());
        stack = resolved.function().invoke(context, stack);
        context.trace().pop();
        return stack;
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
    public Atom unquote(Module module) {
        if (path == null) {
            return builder().withPath(module.name()).build();
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
