package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Module;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Str;

public final class Omodule implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Str name = stack.consume(Str.class);
        if (context.has(name.get())) {
            throw new RuntimeError("INTERPRET", "Module " + name.get() + " already exists");
        }
        Module module = new Module(name.get()).require(context.get("core"));
        context.load(module);
        return stack;
    }
}
