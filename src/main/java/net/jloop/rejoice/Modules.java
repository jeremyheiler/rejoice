package net.jloop.rejoice;

import java.util.HashMap;

public class Modules {

    private final HashMap<String, Module> modules = new HashMap<>();

    public void add(Module module) {
        if (modules.containsKey(module.name())) {
            throw new RuntimeError("INTERPRET", "Module '" + module.name() + "' already exists");
        }
        modules.put(module.name(), module);
    }

    public boolean exists(String name) {
        return modules.containsKey(name);
    }

    public Module resolve(String name) {
        Module module = modules.get(name);
        if (module == null) {
            throw new RuntimeError("INTERPRET", "Module '" + name + "' doesn't exist");
        }
        return module;
    }
}
