package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Module;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Str;

public final class F_module implements Function {

    @Override
    public Stack invoke(Context context, Stack stack) {
        Str name = stack.consume(Str.class);
        if (context.modules().exists(name.get())) {
            throw new RuntimeError("INTERPRET","Module '" + name.get() + "' already exists");
        }
        Module module = new Module(name.get());
        module.require(context.modules().resolve("core"));
        context.modules().add(module);
        context.activate(module);
        return stack;
    }
}
