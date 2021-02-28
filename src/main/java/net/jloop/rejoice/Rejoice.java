package net.jloop.rejoice;

import net.jloop.rejoice.operators.ApplyOperator;
import net.jloop.rejoice.operators.ConcatOperator;
import net.jloop.rejoice.operators.MinusOperator;
import net.jloop.rejoice.operators.PlusOperator;
import net.jloop.rejoice.operators.ReduceOperator;

public class Rejoice {

    private final Parser parser = new Parser();
    private final Library library = new Library();

    public Rejoice() {
        library.define("+", new PlusOperator());
        library.define("-", new MinusOperator());
        library.define("apply", new ApplyOperator());
        library.define("concat", new ConcatOperator());
        library.define("reduce", new ReduceOperator());
    }

    public Stack interpret(String input) {
        return interpret(new Stack(), input);
    }

    public Stack interpret(Stack stack, String input) {
        return parser.parse(new Parser.Buffer(input)).evaluate(library, stack);
    }
}
