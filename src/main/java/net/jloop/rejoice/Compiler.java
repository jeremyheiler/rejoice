package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class Compiler {

    private final Map<Symbol, Macro> macros;
    private final Map<Symbol, Function> functions;

    public Compiler(Map<Symbol, Macro> macros, Map<Symbol, Function> functions) {
        this.macros = macros;
        this.functions = functions;
    }

    public Iterable<Value> iterable(Iterator<Atom> atoms) {
        return () -> new Iterator<>() {

            private final ArrayList<Value> values = new ArrayList<>();

            @Override
            public boolean hasNext() {
                if (values.isEmpty()) {
                    if (atoms.hasNext()) {
                        for (Value value : compile(atoms.next(), atoms)) {
                            values.add(value);
                        }
                        return hasNext();
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            }

            @Override
            public Value next() {
                // If the values list is empty, and hasNext() wasn't called to
                // collect more, however next() should still succeed if it can.
                if (values.isEmpty()) {
                    for (Value value : compile(atoms.next(), atoms)) {
                        values.add(value);
                    }
                    return next();
                } else {
                    return values.remove(0);
                }
            }
        };
    }

    public Iterable<Value> compile(Atom atom, Iterator<Atom> atoms) {
        ArrayList<Value> values = new ArrayList<>();
        if ((atom instanceof Symbol) && macros.containsKey(atom)) {
            for (Value value : macros.get(atom).evaluate(this, atoms)) {
                values.add(value);
            }
        } else {
            values.add(atom);
        }
        return values;
    }

    public void define(Symbol name, Iterable<Value> body) {
        functions.put(name, (stack, interpreter) -> interpreter.interpret(stack, body));
    }
}
