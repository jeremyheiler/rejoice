package net.jloop.rejoice.functions;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Operator;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Quote;

public class Olist implements Operator {

    @Override
    public Stack evaluate(Stack stack) {
        Int64 n = stack.consume(Int64.class);
        List list = new List();
        for (long i = 0; i < n.get(); ++i) {
            Atom atom = stack.consume(Atom.class);
            if (atom instanceof Quote) {
                list.prepend(((Quote) atom).get());
            } else {
                list.prepend(atom);
            }
        }
        return stack.push(list);
    }
}
