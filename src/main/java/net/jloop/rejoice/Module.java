package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Module {

    private final Map<Symbol, Definition> definitions = new HashMap<>();
    private final List<Module> includes = new ArrayList<>();
    private final List<Module> requires = new ArrayList<>();

    private final String name;

    public Module(String name) {
        this.name = name;
    }

    public Module include(Module module) {
        // TODO: Check for name collisions.
        includes.add(0, module);
        return this;
    }

    public Module require(Module module) {
        requires.add(0, module);
        return this;
    }

    public Module define(Symbol name, Function function) {
        define(name, function, true);
        return this;
    }

    public Module define(Symbol name, Function function, boolean pub) {
        if (name.namespace().isPresent()) {
            throw new RuntimeError("MODULE", "Cannot use a namespaced symbol when defining a function");
        }
        // TODO: Maybe remove this restriction?
        if (definitions.containsKey(name)) {
            throw new RuntimeError("MODULE", "Attempted to redefine a function with the name '" + name.print() + "'");
        }
        definitions.put(name, new Definition(name, function, pub));
        return this;
    }

    public Function lookup(Context context, Symbol name) {
        Optional<Definition> definition = get(context, name);
        if (definition.isPresent()) {
            if (definition.get().isPublic()) {
                return definition.get().function();
            } else {
                throw new RuntimeError("MODULE", "Function '" + name.name() + "' is private");
            }
        } else {
            throw new RuntimeError("MODULE", "Function '" + name.name() + "' was not found");
        }
    }

    public Optional<Definition> get(Context context, Symbol name) {
        if (name.namespace().isPresent()) {
            return context.get(name.namespace().get()).get(context, Symbol.of(name.name()));
        } else if (definitions.containsKey(name)) {
            return Optional.of(definitions.get(name));
        } else {
            for (Module module : includes) {
                Optional<Definition> definition = module.get(context, name);
                if (definition.isPresent()) {
                    return definition;
                }
            }
            if (this == context.current()) {
                for (Module module : requires) {
                    Optional<Definition> definition = module.get(context, name);
                    if (definition.isPresent()) {
                        return definition;
                    }
                }
            }
            return Optional.empty();
        }
    }

    public String name() {
        return name;
    }
}
