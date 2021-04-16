package net.jloop.rejoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class Module {

    private final Map<String, Function> functions = new HashMap<>();
    private final Map<String, Supplier<? extends Value>> types = new HashMap<>();
    private final List<Module> includes = new ArrayList<>();
    private final List<Module> requires = new ArrayList<>();

    private final String name;

    public Module(String name) {
        this.name = name;
    }

    public void include(Module module) {
        // TODO: Check for name collisions.
        includes.add(0, module);
    }

    public void require(Module module) {
        requires.add(0, module);
    }

    public void define(String name, Function function) {
        // TODO: Restrict what is allowed in a name?
        // TODO: Maybe remove this restriction?
        if (functions.containsKey(name)) {
            throw new RuntimeError("MODULE", "Attempted to redefine a function with the name '" + name + "'");
        }
        functions.put(name, function);
    }

    public void defineType(String name, Supplier<? extends Value> constructor) {
        if (types.containsKey(name)) {
            throw new RuntimeError("MODULE", "Attempted to redefine a type with the name '" + name + "'");
        }
        types.put(name, constructor);
    }

    public Supplier<? extends Value> lookupTypeConstructor(String name) {
        if (types.containsKey(name)){
            Supplier<? extends Value> constructor = types.get(name);
            if (constructor == null) {
                throw new RuntimeError("MODULE", "The type '" + name + "' cannot be constructed using 'new'");
            } else {
                return constructor;
            }
        } else {
            throw new RuntimeError("MODULE", "Unknown type: '" + name + "'");
        }
    }

    public static final class Resolved {

        private final ArrayList<String> includes = new ArrayList<>();

        private final Module module;
        private final Function function;
        private final String name;

        public Resolved(Module module, Function function, String name) {
            this.module = module;
            this.function = function;
            this.name = name;
        }

        public Trace.Call toCall() {
            return new Trace.Call(includes, module().name(), name);
        }

        public List<String> includes() {
            return includes;
        }

        public Module module() {
            return module;
        }

        public Function function() {
            return function;
        }

        public String name() {
            return name;
        }
    }

    public Optional<Resolved> resolve(Module caller, String name) {
        if (functions.containsKey(name)) {
            return Optional.of(new Resolved(this, functions.get(name), name));
        } else {
            Optional<Resolved> resolved = resolveFromIncludes(name);
            if (resolved.isPresent()) {
                return resolved;
            }
            if (this == caller) {
                for (Module module : requires) {
                    resolved = module.resolve(caller, name);
                    if (resolved.isPresent()) {
                        return resolved;
                    }
                }
            }
            return Optional.empty();
        }
    }

    private Optional<Resolved> resolveFromIncludes(String name) {
        for (Module module : includes) {
            if (module.functions.containsKey(name)) {
                Resolved resolved = new Resolved(this, module.functions.get(name), name);
                resolved.includes().add(module.name);
                return Optional.of(resolved);
            } else {
                Optional<Resolved> resolved = module.resolveFromIncludes(name);
                if (resolved.isPresent()) {
                    resolved.get().includes().add(this.name);
                    return resolved;
                }
            }
        }
        return Optional.empty();
    }

    public String name() {
        return name;
    }
}
