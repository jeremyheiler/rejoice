package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Env;
import net.jloop.rejoice.Invocable;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Quotable;
import net.jloop.rejoice.Value;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Symbol implements Atom, Quotable {

    private final String namespace;
    private final String name;

    private Symbol(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public static Symbol of(String symbol) {
        int i = symbol.indexOf('/');
        if (i == -1 || symbol.equals("/")) {
            return new Symbol(null, symbol);
        } else {
            return new Symbol(symbol.substring(0, i), symbol.substring(i + 1));
        }
    }

    public static Symbol of(String namespace, String name) {
        return new Symbol(namespace, name);
    }

    @Override
    public void collect(Env env, Iterator<Value> input, List<Value> collection) {
        Invocable invocable = env.resolve(this);
        if (invocable instanceof Macro) {
            env.trace().push(this);
            Iterator<Value> macro = ((Macro) invocable).rewrite(env, input);
            env.trace().pop();
            while (macro.hasNext()) {
                collection.add(macro.next());
            }
        } else {
            collection.add(this);
        }
    }

    @Override
    public Stack interpret(Env env, Stack stack, Iterator<Value> input) {
        env.trace().push(this);
        stack = env.resolve(this).invoke(env, stack, input);
        env.trace().pop();
        return stack;
    }

    public Optional<String> namespace() {
        return Optional.ofNullable(namespace);
    }

    public String name() {
        return name;
    }

    @Override
    public Symbol symbol() {
        return this;
    }

    @Override
    public Atom quote() {
        return new Quote(this);
    }

    @Override
    public String print() {
        return namespace == null ? name : namespace + "/" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(namespace, symbol.namespace) && name.equals(symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name);
    }
}
