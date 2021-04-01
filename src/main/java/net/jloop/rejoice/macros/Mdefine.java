package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Compiler;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;

public final class Mdefine implements Macro {

    private final Symbol separator;
    private final Symbol terminator;

    public Mdefine(Symbol separator, Symbol terminator) {
        this.separator = separator;
        this.terminator = terminator;
    }

    @Override
    public Iterable<Value> evaluate(Compiler compiler, Iterator<Atom> atoms) {

        // Find the name for the new definition

        Symbol name = null;
        ArrayList<Value> extra = new ArrayList<>();
        while (atoms.hasNext()) {
            for (Value value : compiler.compile(atoms.next(), atoms)) {
                if (name == null) {
                    if (value instanceof Symbol) {
                        name = (Symbol) value;
                    } else {
                        throw new RuntimeError("MACRO", "Expecting a Symbol for the definition name, but found " + value.getClass().getSimpleName());
                    }
                } else {
                    extra.add(value);
                }
            }
            if (name != null) {
                break;
            }
        }
        if (name == null) {
            throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete definition");
        }

        // Find the separator symbol for the definition

        boolean found = false;
        for (Value value : extra) {
            if (value.equals(separator)) {
                found = true;
                break;
            }
        }
        if (!found) {
            extra = new ArrayList<>();
            while (atoms.hasNext()) {
                for (Value value : compiler.compile(atoms.next(), atoms)) {
                    if (found) {
                        extra.add(value);
                    } else if (value instanceof Symbol) {
                        if (value.equals(separator)) {
                            found = true;
                        }
                    } else {
                        throw new RuntimeError("MACRO", "Expecting the Symbol '" + separator.getName() + "' after the definition name, but found " + value.getClass().getSimpleName());
                    }
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete definition");
            }
        }

        // Collect the body for the definition

        ArrayList<Value> body = extra;
        extra = new ArrayList<>();
        boolean terminated = false;
        while (atoms.hasNext()) {
            for (Value value : compiler.compile(atoms.next(), atoms)) {
                if (terminated) {
                    extra.add(value);
                } else if (value.equals(terminator)) {
                    terminated = true;
                } else {
                    body.add(value);
                }
            }
            if (terminated) {
                break;
            }
        }
        if (!terminated) {
            throw new RuntimeError("MACRO", "Unexpected EOF; Incomplete definition");
        }

        // Create the definition

        compiler.define(name, body);
        return extra;
    }
}
