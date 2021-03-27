package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;

// x [... [x xs] ...] -> [xs]
// Given a list of lists, return the rest of the first list where the type of the first element of that list matches the type of x.
// If none match, return the last list. If there's only one sub list, return the whole sub list.

public final class Oopcase implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        List list = stack.consume(List.class);
        Atom item = stack.consume(Atom.class);
        if (list.size() == 0) {
            throw new RuntimeException("LIST cannot be empty");
        }
        if (list.size() == 1) {
            return stack.push(list.first());
        }
        int count = 0;
        for (Atom el : list) {
            if (el instanceof List) {
                List l = (List) el;
                if (++count == list.size()) { // last list
                    return stack.push(l);
                }
                if (l.size() == 0) {
                    throw new RuntimeException("LIST cannot be empty");
                }
                if (l.first().getClass() == item.getClass()) {
                    return stack.push(l.rest());
                }
            } else {
                throw new RuntimeException("Expecting a LIST");
            }
        }
        return stack;
    }
}
