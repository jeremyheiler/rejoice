package net.jloop.rejoice;

import java.util.HashMap;
import java.util.Map;

public final class Context {

    private final Interpreter interpreter;
    private final Map<String, Module> modules = new HashMap<>();
    private Module current;

    public Context(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Interpreter interpreter() {
        return interpreter;
    }

    public void load(Module module) {
        add(module);
        current = module;
    }

    public Module current() {
        return current;
    }

    public void add(Module module) {
        if (modules.containsKey(module.name())) {
            throw new RuntimeError("INTERPRET", "Module " + module.name() + " already exists");
        }
        modules.put(module.name(), module);
    }

    public boolean has(String name) {
        return modules.containsKey(name);
    }

    public Module get(String name) {
        Module module = modules.get(name);
        if (module == null) {
            throw new RuntimeError("INTERPRET", "Module " + name + " doesn't exist");
        }
        return module;
    }
}
