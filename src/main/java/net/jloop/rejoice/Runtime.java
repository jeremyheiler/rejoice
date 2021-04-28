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
import net.jloop.rejoice.functions.F_define_E_;
import net.jloop.rejoice.functions.F_if;
import net.jloop.rejoice.functions.F_include;
import net.jloop.rejoice.functions.F_list;
import net.jloop.rejoice.functions.F_list_length;
import net.jloop.rejoice.functions.F_module;
import net.jloop.rejoice.functions.F_new;
import net.jloop.rejoice.functions.F_pop;
import net.jloop.rejoice.functions.F_print;
import net.jloop.rejoice.functions.F_require;
import net.jloop.rejoice.functions.F_roll_down;
import net.jloop.rejoice.functions.F_roll_up;
import net.jloop.rejoice.functions.F_stack;
import net.jloop.rejoice.functions.F_stack_apply;
import net.jloop.rejoice.functions.F_stack_demote;
import net.jloop.rejoice.functions.F_stack_get;
import net.jloop.rejoice.functions.F_stack_peek;
import net.jloop.rejoice.functions.F_stack_pop;
import net.jloop.rejoice.functions.F_stack_promote;
import net.jloop.rejoice.functions.F_stack_push;
import net.jloop.rejoice.functions.F_stack_take;
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
import net.jloop.rejoice.macros.M_Stack;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.util.ReaderIterator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Runtime {

    private final Interpreter interpreter;
    private final Parser parser;
    private final Lexer lexer;
    private final Context context;
    private Stack stack;

    public Runtime(Interpreter interpreter, Parser parser, Lexer lexer, Context context) {
        this(interpreter, parser, lexer, context, new Stack());
    }

    public Runtime(Interpreter interpreter, Parser parser, Lexer lexer, Context context, Stack stack) {
        this.interpreter = interpreter;
        this.parser = parser;
        this.lexer = lexer;
        this.context = context;
        this.stack = stack;
    }

    public void load(InputStream source) {
        eval(new InputStreamReader(source));
    }

    public void load(Module module, InputStream source) {
        context.modules().add(module);
        context.activate(module);
        load(source);
    }

    public void eval(String input) {
        eval(new StringReader(input));
    }

    public void eval(Reader input) {
        stack = interpreter.reduce(stack, parser.map(lexer.map(new ReaderIterator(input))));
    }

    public void eval(Function function) {
        stack = function.invoke(context, stack);
    }

    public Context context() {
        return context;
    }

    public Stack stack() {
        return stack;
    }

    public static Runtime create() {
        // Define internal module
        Module internal = new Module("internal");
        internal.define("/", new O_Divide());
        internal.define("-", new O_Minus());
        internal.define("%", new O_Modulus());
        internal.define("*", new O_Multiply());
        internal.define("+", new O_Plus());
        internal.define("abs", new Oabs());
        internal.define("app1", new Capp1());
        internal.define("app2", new Capp2());
        internal.define("app3", new Capp3());
        internal.define("b", new Cb());
        internal.define("choice", new Ochoice());
        internal.define("cleave", new Ccleave());
        internal.define("%list-cons", new F_cons());
        internal.define("define!", new F_define_E_());
        internal.define("dipd", new Cdipd());
        internal.define("dipdd", new Cdipdd());
        internal.define("equal?", new Oequal_Q_());
        internal.define("i", new Ci());
        internal.define("%if", new F_if());
        internal.define("%include", new F_include());
        internal.define("%list", new F_list());
        internal.define("%list-length", new F_list_length());
        internal.define("map", new Cmap());
        internal.define("max", new Omax());
        internal.define("min", new Omin());
        internal.define("module", new F_module());
        internal.define("%new", new F_new());
        internal.define("nullary", new Cnullary());
        internal.define("opcase", new Oopcase());
        internal.define("%pop", new F_pop());
        internal.define("%print", new F_print());
        internal.define("%require", new F_require());
        internal.define("%roll-down", new F_roll_down());
        internal.define("%roll-up", new F_roll_up());
        internal.define("sign", new Osign());
        internal.define("%stack", new F_stack());
        internal.define("%stack-apply", new F_stack_apply());
        internal.define("%stack-demote", new F_stack_demote());
        internal.define("%stack-get", new F_stack_get());
        internal.define("%stack-peek", new F_stack_peek());
        internal.define("%stack-pop", new F_stack_pop());
        internal.define("%stack-promote", new F_stack_promote());
        internal.define("%stack-push", new F_stack_push());
        internal.define("%stack-take", new F_stack_take());
        internal.define("while", new Cwhile());
        internal.define("%write", new F_write());
        internal.define("x", new Cx());
        internal.define("y", new Cy());
        // Define global macros
        Map<String, Macro> macros = new HashMap<>();
        macros.put("(", new M_List());
        macros.put("[", new M_Stack());
        macros.put("define", new M_Define());
        // Setup context
        Context context = new Context(macros);
        context.modules().add(internal);
        // Configure phases
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Interpreter interpreter = new Interpreter(context);
        // Initialize runtime
        return new Runtime(interpreter, parser, lexer, context);
    }
}
