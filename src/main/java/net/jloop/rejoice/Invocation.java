package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Invocation {

    private final ArrayList<String> includes = new ArrayList<>();

    private final Module module;
    private final Invocable invocable;
    private final String name;

    public Invocation(Module module, Invocable invocable, String name) {
        this.module = module;
        this.invocable = invocable;
        this.name = name;
    }

    public Stack invoke(Env env, Stack stack, Iterator<Value> input) {
        Trace trace = env.trace();
        trace.push(this);
        stack = invocable.invoke(env, stack, input);
        trace.pop();
        return stack;
    }

    public List<String> includes() {
        return includes;
    }

    public Module module() {
        return module;
    }

    public Invocable invocable() {
        return invocable;
    }

    public String name() {
        return module.name() + "/" + name;
    }
}
