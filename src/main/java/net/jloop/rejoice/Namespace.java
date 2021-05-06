package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Map;

public final class Namespace {

    private final Map<String, Invocable> invocables = new HashMap<>();
    private final Map<String, Namespace> namespaces = new HashMap<>();

    private final String name;

    public Namespace(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void add(String name, Invocable invocable) {
        invocables.put(name, invocable);
    }

    private void add(String namespace, String name, Invocable invocable) {
        int index = namespace.indexOf('.');
        if (index == -1) {
            Namespace ns = namespaces.computeIfAbsent(namespace, Namespace::new);
            ns.add(name, invocable);
        } else {
            String segment = namespace.substring(0, index);
            Namespace ns = namespaces.computeIfAbsent(segment, Namespace::new);
            ns.add(namespace.substring(index + 1), name, invocable);
        }
    }

    public void add(Symbol symbol, Invocable invocable) {
        if (symbol.namespace().isPresent()) {
            add(symbol.namespace().get(), symbol.name(), invocable);
        } else {
            add(symbol.name(), invocable);
        }
    }

    private Invocable resolve(String namespace, String name) {
        if (invocables.containsKey(name)) {
            return invocables.get(name);
        } else {
            if (namespace == null) {
                throw new RuntimeError("NS", "Symbol '" + name + "' was not found in the proto namespace");
            } else {
                throw new RuntimeError("NS", "Symbol '" + name + "' was not found in the namespace '" + namespace + "'");
            }
        }
    }

    private Invocable resolve(String namespace, String rest, String name) {
        int index = rest.indexOf('.');
        if (index == -1) {
            Namespace ns = namespaces.get(rest);
            if (ns == null) {
                throw new RuntimeError("NS", "Namespace '" + namespace + "' was not found");
            }
            return ns.resolve(namespace, name);
        } else {
            String segment = rest.substring(0, index);
            Namespace ns = namespaces.get(segment);
            if (ns == null) {
                throw new RuntimeError("NS", "Namespace '" + namespace + "' was not found");
            }
            return ns.resolve(namespace, rest.substring(index + 1), name);
        }
    }

    public Invocable resolve(Symbol symbol) {
        if (symbol.namespace().isPresent()) {
            return resolve(symbol.namespace().get(), symbol.namespace().get(), symbol.name());
        } else {
            return resolve(null, symbol.name());
        }
    }
}
