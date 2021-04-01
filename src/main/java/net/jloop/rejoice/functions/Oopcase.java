package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.List;

// x [... [x xs] ...] -> [xs]
// Given a list of lists, return the rest of the first list where the type of the first element of that list matches the type of x.
// If none match, return the last list. If there's only one sub list, return the whole sub list.

public final class Oopcase implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        List list = stack.consume(List.class);
        Atom atom = stack.consume(Atom.class);
        if (list.size() == 0) {
            throw new RuntimeError("INTERPRET", "Operator 'opcase': list cannot be empty");
        }
        if (list.size() == 1) {
            return stack.push(list.first());
        }
        int count = 0;
        for (Value value : list) {
            if (value instanceof List) {
                List l = (List) value;
                if (++count == list.size()) { // last list
                    return stack.push(l);
                }
                if (l.size() == 0) {
                    throw new RuntimeException("LIST cannot be empty");
                }
                if (l.first().getClass() == atom.getClass()) {
                    return stack.push(l.rest());
                }
            } else {
                throw new RuntimeException("Expecting a LIST");
            }
        }
        return stack;
    }
}
