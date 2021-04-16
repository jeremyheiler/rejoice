package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Type;

import java.util.function.Supplier;

public final class F_new implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Type type = stack.consume(Type.class);
        Supplier<? extends Value> constructor = context.active().lookupTypeConstructor(type.name());
        return stack.push(constructor.get());
    }
}
