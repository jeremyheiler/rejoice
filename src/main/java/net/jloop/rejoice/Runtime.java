package net.jloop.rejoice;

import net.jloop.rejoice.functions.F_apply;
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
import net.jloop.rejoice.functions.F_prepare_if;
import net.jloop.rejoice.functions.F_prepare_when;
import net.jloop.rejoice.functions.F_print;
import net.jloop.rejoice.functions.F_promote;
import net.jloop.rejoice.functions.F_sink;
import net.jloop.rejoice.functions.F_stack;
import net.jloop.rejoice.functions.F_stack_apply;
import net.jloop.rejoice.functions.F_stack_length;
import net.jloop.rejoice.functions.F_stack_push;
import net.jloop.rejoice.functions.F_when;
import net.jloop.rejoice.functions.F_while;
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
import net.jloop.rejoice.macros.M_native;
import net.jloop.rejoice.macros.M_protocol;
import net.jloop.rejoice.macros.M_quote;
import net.jloop.rejoice.macros.M_stack;
import net.jloop.rejoice.types.Stack;

import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Runtime {

    private final Env env;
    private Stack data = new Stack();

    public Runtime(Env env) {
        this.env = env;
    }

    public Env env() {
        return env;
    }

    public Stack stack() {
        return data;
    }

    public void eval(Reader input) {
        Deque<Value> values = new Parser().parse(new Lexer().lex(input));
        Deque<Deque<Value>> call = new ArrayDeque<>();
        data = new Interpreter().interpret(env, data, call);
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
        env.define("apply", new F_apply());
        env.define("choice", new Ochoice());
        env.define("cons", new F_cons());
        env.define("define-function", new F_define_function());
        env.define("define-protocol", new F_define_protocol());
        env.define("demote", new F_demote());
        env.define("equal?", new Oequal_Q_());
        env.define("extend-protocol", new F_extend_protocol());
        env.define("if", new F_if());
        env.define("lift", new F_lift());
        env.define("list", new F_list());
        env.define("list-length", new F_list_length());
        env.define("max", new Omax());
        env.define("min", new Omin());
        env.define("opcase", new Oopcase());
        env.define("peek", new F_peek());
        env.define("pop", new F_pop());
        env.define("prepare-if", new F_prepare_if());
        env.define("prepare-when", new F_prepare_when());
        env.define("print", new F_print());
        env.define("promote", new F_promote());
        env.define("push", new F_stack_push());
        env.define("sign", new Osign());
        env.define("sink", new F_sink());
        env.define("stack", new F_stack());
        env.define("stack-apply", new F_stack_apply());
        env.define("stack-length", new F_stack_length());
        env.define("when", new F_when());
        env.define("while", new F_while());
        env.define("write", new F_write());
        // Macros
        env.define("[", new M_quote());
        env.define("%[", new M_stack());
        env.define("extend", new M_extend());
        env.define("function", new M_function());
        env.define("native", new M_native());
        env.define("protocol", new M_protocol());
        return new Runtime(env);
    }
}
