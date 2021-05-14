package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;
import net.jloop.rejoice.types.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Protocol implements Function {

    private final Map<Type, Function> extensions = new HashMap<>();
    private final Symbol name;

    public Protocol(Symbol name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public Stack call(Env env, Stack stack) {
        Value value = stack.peek();
        Function function = extensions.get(value.type());
        if (function == null) {
            throw new RuntimeError("INTERPRET", "Protocol '" + name.name() + "' does is not extended by type '" + value.type().print() + "'");
        }
        return function.call(env, stack);
    }

    public void extend(Type type, Function function) {
        extensions.put(type, function);
    }
}
