package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Type;

import java.util.function.Supplier;

public final class F_new implements Function {

    @Override
    public Stack evaluate(Env env, Stack stack) {
        Type type = stack.consume(Type.class);
        Supplier<? extends Value> constructor = env.active().lookupTypeConstructor(type.name());
        return stack.push(constructor.get());
    }
}
