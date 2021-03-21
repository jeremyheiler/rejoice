package net.jloop.rejoice;

import java.io.PushbackReader;
import java.io.StringReader;

public class Rejoice {

    private final Parser parser = new Parser();
    private final Library library = new Library();
    private final Interpreter interpreter = new Interpreter(parser, library);

    public Rejoice() {
        library.define("/", Operators::_divide);
        library.define("=", Operators::_equal);
        library.define("-", Operators::_minus);
        library.define("%", Operators::_mod);
        library.define("*", Operators::_multiply);
        library.define("+", Operators::_plus);
        library.define("abs", Operators::abs);
        library.define("app1", Operators::app1);
        library.define("app2", Operators::app2);
        library.define("app3", Operators::app3);
        library.define("b", Operators::b);
        library.define("choice", Operators::choice);
        library.define("cleave", Operators::cleave);
        library.define("dip", Operators::dip);
        library.define("dipd", Operators::dipd);
        library.define("dipdd", Operators::dipdd);
        library.define("dup", Operators::dup);
        library.define("dupd", Operators::dupd);
        library.define("i", Operators::i);
        library.define("ifte", Operators::ifte);
        library.define("map", Operators::map);
        library.define("min", Operators::min);
        library.define("max", Operators::max);
        library.define("nullary", Operators::nullary);
        library.define("opcase", Operators::opcase);
        library.define("pop", Operators::pop);
        library.define("popd", Operators::popd);
        library.define("popop", Operators::popop);
        library.define("pred", Operators::pred);
        library.define("rolldown", Operators::rolldown);
        library.define("rollup", Operators::rollup);
        library.define("sign", Operators::sign);
        library.define("succ", Operators::succ);
        library.define("swap", Operators::swap);
        library.define("swapd", Operators::swapd);
        library.define("while", Operators::_while);
        library.define("x", Operators::x);
        library.define("y", Operators::y);
    }

    public InterpreterResult evaluate(String input) {
        return evaluate(new Stack(), input);
    }

    public InterpreterResult evaluate(Stack stack, String input) {
        return interpreter.interpret(stack, new PushbackReader(new StringReader(input)));
    }
}
