package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.Map;
import java.util.Optional;

public final class Context {

    private final Map<String, Macro> macros;
    private final Modules modules = new Modules();
    private final Trace trace = new Trace();

    private Module active;

    public Context(Map<String, Macro> macros) {
        this.macros = macros;
    }

    public Module.Resolved resolve(Symbol symbol) {
        Module module = moduleFor(symbol);
        Optional<Module.Resolved> resolved = module.resolve(active, symbol.name());
        if (resolved.isPresent()) {
            return resolved.get();
        } else {
            throw new RuntimeError("INTERPRET", "Function '" + symbol.name() + "' was not found when called from module '" + module.name() + "'");
        }
    }

    private Module moduleFor(Symbol symbol) {
        Optional<String> path = symbol.path();
        if (path.isPresent()) {
            return modules.resolve(path.get());
        } else {
            return active;
        }
    }

    public Map<String, Macro> macros() {
        return macros;
    }

    public Modules modules() {
        return modules;
    }

    public Trace trace() {
        return trace;
    }

    public Module active() {
        return active;
    }

    public void activate(Module module) {
        active = module;
    }
}
