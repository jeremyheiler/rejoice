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
import net.jloop.rejoice.functions.F_define_function;
import net.jloop.rejoice.functions.F_if;
import net.jloop.rejoice.functions.F_lift;
import net.jloop.rejoice.functions.F_list_cons;
import net.jloop.rejoice.functions.F_list_create;
import net.jloop.rejoice.functions.F_list_length;
import net.jloop.rejoice.functions.F_print;
import net.jloop.rejoice.functions.F_push;
import net.jloop.rejoice.functions.F_stack_apply;
import net.jloop.rejoice.functions.F_stack_create;
import net.jloop.rejoice.functions.F_stack_demote;
import net.jloop.rejoice.functions.F_stack_peek;
import net.jloop.rejoice.functions.F_stack_pop;
import net.jloop.rejoice.functions.F_stack_promote;
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
import net.jloop.rejoice.macros.M_Define;
import net.jloop.rejoice.macros.M_List;
import net.jloop.rejoice.macros.M_Native;
import net.jloop.rejoice.macros.M_Stack;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.io.Reader;

public class Runtime {

    private final Env env;
    private Stack stack;

    public Runtime(Env env) {
        this(env, new Stack());
    }

    public Runtime(Env env, Stack stack) {
        this.env = env;
        this.stack = stack;
    }

    public Env env() {
        return env;
    }

    public Stack stack() {
        return stack;
    }

    public void eval(Reader input) {
        stack = env.eval(stack, input);
    }

    public static Runtime create() {
        // Namespace: proto
        Namespace proto = new Namespace("proto");
        // Core Functions
        proto.add("/", new O_Divide());
        proto.add("-", new O_Minus());
        proto.add("%", new O_Modulus());
        proto.add("*", new O_Multiply());
        proto.add("+", new O_Plus());
        proto.add("abs", new Oabs());
        proto.add("app1", new Capp1());
        proto.add("app2", new Capp2());
        proto.add("app3", new Capp3());
        proto.add("b", new Cb());
        proto.add("choice", new Ochoice());
        proto.add("cleave", new Ccleave());
        proto.add("define-function", new F_define_function());
        proto.add("dipd", new Cdipd());
        proto.add("dipdd", new Cdipdd());
        proto.add("equal?", new Oequal_Q_());
        proto.add("i", new Ci());
        proto.add("if", new F_if());
        proto.add("lift", new F_lift());
        proto.add("map", new Cmap());
        proto.add("max", new Omax());
        proto.add("min", new Omin());
        proto.add("nullary", new Cnullary());
        proto.add("opcase", new Oopcase());
        proto.add("print", new F_print());
        proto.add("push", new F_push());
        proto.add("sign", new Osign());
        proto.add("while", new Cwhile());
        proto.add("write", new F_write());
        proto.add("x", new Cx());
        proto.add("y", new Cy());
        // Core macros
        proto.add("(", new M_List());
        proto.add("[", new M_Stack());
        proto.add("define", new M_Define());
        proto.add("native", new M_Native());
        // Namespace: proto.list
        proto.add(Symbol.of("list/cons"), new F_list_cons());
        proto.add(Symbol.of("list/create"), new F_list_create());
        proto.add(Symbol.of("list/length"), new F_list_length());
        // Namespace: proto.stack
        proto.add(Symbol.of("stack/apply"), new F_stack_apply());
        proto.add(Symbol.of("stack/create"), new F_stack_create());
        proto.add(Symbol.of("stack/demote"), new F_stack_demote());
        proto.add(Symbol.of("stack/peek"), new F_stack_peek());
        proto.add(Symbol.of("stack/pop"), new F_stack_pop());
        proto.add(Symbol.of("stack/promote"), new F_stack_promote());
        proto.add(Symbol.of("stack/push"), new F_stack_push());
        // Setup environment
        Env env = new Env(proto);
        // Initialize runtime
        return new Runtime(env);
    }
}
