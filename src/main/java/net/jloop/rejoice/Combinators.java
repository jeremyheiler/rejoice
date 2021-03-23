package net.jloop.rejoice;

import net.jloop.rejoice.types.Bool;
import net.jloop.rejoice.types.List;

public class Combinators {

    // x [p] -> r
    // Replace x with the result of evaluating the quote p with a stack containing only x.
    public static Stack app1(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        Atom r = interpreter.interpret(new Stack().push(x), p).consume(Atom.class);
        return stack.push(r);
    }

    // x y [p] -> rx ry
    // Replace x and y with the results of evaluating the quote p with a stack containing only x or y, respectively.
    public static Stack app2(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = interpreter.interpret(new Stack().push(y), p).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = interpreter.interpret(new Stack().push(x), p).consume(Atom.class);
        return stack.push(rx).push(ry);
    }

    // x y z [p] -> rx ry rz
    // Replace x, y, and z with the results of evaluating the quote p with a stack containing only x, y, or z, respectively.
    public static Stack app3(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom rz = interpreter.interpret(new Stack().push(z), p).consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = interpreter.interpret(new Stack().push(y), p).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = interpreter.interpret(new Stack().push(x), p).consume(Atom.class);
        return stack.push(rx).push(ry).push(rz);
    }

    // [p] [q] -> ~rp ~rq
    public static Stack b(Stack stack, Interpreter interpreter) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        stack = interpreter.interpret(stack, p);
        stack = interpreter.interpret(stack, q);
        return stack;
    }

    // x [p] [q] -> ~rp ~rq
    public static Stack cleave(Stack stack, Interpreter interpreter) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        Stack copyP = stack.copy();
        Stack copyQ = stack.copy();
        Atom rp = interpreter.interpret(copyP, p).consume(Atom.class);
        Atom rq = interpreter.interpret(copyQ, q).consume(Atom.class);
        return stack.pop().push(rp).push(rq);
    }

    // [p] -> ...
    public static Stack i(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        return interpreter.interpret(stack, p);
    }

    // [b] [t] [f] -> ...
    public static Stack ifte(Stack stack, Interpreter interpreter) {
        List f = stack.consume(List.class);
        List t = stack.consume(List.class);
        List b = stack.consume(List.class);
        Stack copy = stack.copy();
        if (interpreter.interpret(copy, b).consume(Bool.class).isTrue()) {
            return interpreter.interpret(stack, t);
        } else {
            return interpreter.interpret(stack, f);
        }
    }

    // x [p] -> ... x
    public static Stack dip(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        return interpreter.interpret(stack, p).push(x);
    }

    // x y [p] -> ... x y
    public static Stack dipd(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return interpreter.interpret(stack, p).push(x).push(y);
    }

    // x y z [p] -> ... x y z
    public static Stack dipdd(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return interpreter.interpret(stack, p).push(x).push(y).push(z);
    }

    // a [p] -> b
    public static Stack map(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        List a = stack.consume(List.class);
        List b = new List();
        for (Atom atom : a) {
            b.append(interpreter.interpret(new Stack().push(atom), p).consume(Atom.class));
        }
        return stack.push(b);
    }

    // [p] -> r
    public static Stack nullary(Stack stack, Interpreter interpreter) {
        List p = stack.consume(List.class);
        Atom r = interpreter.interpret(stack.copy(), p).consume(Atom.class);
        return stack.push(r);
    }

    // [b] [d] -> ...
    public static Stack _while(Stack stack, Interpreter interpreter) {
        List d = stack.consume(List.class);
        List b = stack.consume(List.class);
        while (interpreter.interpret(stack, b).consume(Bool.class).isTrue()) {
            stack = interpreter.interpret(stack, d);
        }
        return stack;
    }

    // [p] -> [p] ...
    public static Stack x(Stack stack, Interpreter interpreter) {
        List p = stack.peek(List.class);
        return interpreter.interpret(stack, p);
    }

    //
    public static Stack y(@SuppressWarnings("unused") Stack stack, @SuppressWarnings("unused") Interpreter interpreter) {
        throw new UnsupportedOperationException("y");
    }
}
