package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Optional;

public class Env {

    private final HashMap<String, Module> modules = new HashMap<>();
    private final Trace trace = new Trace();

    private Module active;

    public Module active() {
        return active;
    }

    public void activate(Module module) {
        active = module;
    }

    public void install(Module module) {
        if (modules.containsKey(module.name())) {
            throw new RuntimeError("INTERPRET", "Module '" + module.name() + "' already exists");
        }
        modules.put(module.name(), module);
    }

    public Module module(String name) {
        Module module = modules.get(name);
        if (module == null) {
            throw new RuntimeError("INTERPRET", "Module '" + name + "' doesn't exist");
        }
        return module;
    }

    public Trace trace() {
        return trace;
    }

    public Invocation resolve(Symbol symbol) {
        Module module = moduleFor(symbol);
        Optional<Invocation> resolved = module.resolve(active, symbol.name());
        if (resolved.isPresent()) {
            return resolved.get();
        } else {
            throw new RuntimeError("INTERPRET", "Function '" + symbol.name() + "' was not found when called from module '" + module.name() + "'");
        }
    }

    private Module moduleFor(Symbol symbol) {
        Optional<String> path = symbol.path();
        if (path.isPresent()) {
            return module(path.get());
        } else {
            return active;
        }
    }
}
