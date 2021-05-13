package net.jloop.rejoice;

import net.jloop.rejoice.functions.Capp1;
import net.jloop.rejoice.functions.Capp2;
import net.jloop.rejoice.functions.Capp3;
import net.jloop.rejoice.functions.Cb;
import net.jloop.rejoice.functions.Ccleave;
import net.jloop.rejoice.functions.Cdipd;
import net.jloop.rejoice.functions.Cdipdd;
import net.jloop.rejoice.functions.Ci;
import net.jloop.rejoice.functions.Cmap;
import net.jloop.rejoice.functions.Cnullary;
import net.jloop.rejoice.functions.Cwhile;
import net.jloop.rejoice.functions.Cx;
import net.jloop.rejoice.functions.Cy;
import net.jloop.rejoice.functions.F_cons;
import net.jloop.rejoice.functions.F_define_function;
import net.jloop.rejoice.functions.F_define_protocol;
import net.jloop.rejoice.functions.F_demote;
import net.jloop.rejoice.functions.F_extend_protocol;
import net.jloop.rejoice.functions.F_if;
import net.jloop.rejoice.functions.F_lift;
import net.jloop.rejoice.functions.F_list;
import net.jloop.rejoice.functions.F_list_length;
import net.jloop.rejoice.functions.F_peek;
import net.jloop.rejoice.functions.F_pop;
import net.jloop.rejoice.functions.F_print;
import net.jloop.rejoice.functions.F_promote;
import net.jloop.rejoice.functions.F_sink;
import net.jloop.rejoice.functions.F_stack;
import net.jloop.rejoice.functions.F_stack_apply;
import net.jloop.rejoice.functions.F_stack_length;
import net.jloop.rejoice.functions.F_stack_push;
import net.jloop.rejoice.functions.F_write;
import net.jloop.rejoice.functions.O_Divide;
import net.jloop.rejoice.functions.O_Minus;
import net.jloop.rejoice.functions.O_Modulus;
import net.jloop.rejoice.functions.O_Multiply;
import net.jloop.rejoice.functions.O_Plus;
import net.jloop.rejoice.functions.Oabs;
import net.jloop.rejoice.functions.Ochoice;
import net.jloop.rejoice.functions.Oequal_Q_;
import net.jloop.rejoice.functions.Omax;
import net.jloop.rejoice.functions.Omin;
import net.jloop.rejoice.functions.Oopcase;
import net.jloop.rejoice.functions.Osign;
import net.jloop.rejoice.macros.M_extend;
import net.jloop.rejoice.macros.M_function;
import net.jloop.rejoice.macros.M_list;
import net.jloop.rejoice.macros.M_native;
import net.jloop.rejoice.macros.M_protocol;
import net.jloop.rejoice.macros.M_stack;
import net.jloop.rejoice.types.Stack;

import java.io.Reader;
import java.util.Iterator;

public class Runtime {

    private final Env env;
    private Stack stack = new Stack();

    public Runtime(Env env) {
        this.env = env;
    }

    public Env env() {
        return env;
    }

    public Stack stack() {
        return stack;
    }

    public void eval(Reader input) {
        Iterator<Value> values = new Parser().map(new Lexer().map(input));
        stack = new Interpreter().interpret(env, stack, values);
    }

    public static Runtime create() {
        Env env = new Env();
        // Functions
        env.define("/", new O_Divide());
        env.define("-", new O_Minus());
        env.define("%", new O_Modulus());
        env.define("*", new O_Multiply());
        env.define("+", new O_Plus());
        env.define("abs", new Oabs());
        env.define("app1", new Capp1());
        env.define("app2", new Capp2());
        env.define("app3", new Capp3());
        env.define("b", new Cb());
        env.define("choice", new Ochoice());
        env.define("cleave", new Ccleave());
        env.define("cons", new F_cons());
        env.define("define-function", new F_define_function());
        env.define("define-protocol", new F_define_protocol());
        env.define("demote", new F_demote());
        env.define("dipd", new Cdipd());
        env.define("dipdd", new Cdipdd());
        env.define("equal?", new Oequal_Q_());
        env.define("extend-protocol", new F_extend_protocol());
        env.define("i", new Ci());
        env.define("if", new F_if());
        env.define("lift", new F_lift());
        env.define("list", new F_list());
        env.define("list-length", new F_list_length());
        env.define("map", new Cmap());
        env.define("max", new Omax());
        env.define("min", new Omin());
        env.define("nullary", new Cnullary());
        env.define("opcase", new Oopcase());
        env.define("peek", new F_peek());
        env.define("pop", new F_pop());
        env.define("print", new F_print());
        env.define("promote", new F_promote());
        env.define("push", new F_stack_push());
        env.define("sign", new Osign());
        env.define("sink", new F_sink());
        env.define("stack", new F_stack());
        env.define("stack-apply", new F_stack_apply());
        env.define("stack-length", new F_stack_length());
        env.define("while", new Cwhile());
        env.define("write", new F_write());
        env.define("x", new Cx());
        env.define("y", new Cy());
        // Macros
        env.define("(", new M_list());
        env.define("[", new M_stack());
        env.define("extend", new M_extend());
        env.define("function", new M_function());
        env.define("native", new M_native());
        env.define("protocol", new M_protocol());
        return new Runtime(env);
    }
}
