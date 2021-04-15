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
import net.jloop.rejoice.functions.F_list;
import net.jloop.rejoice.functions.F_pop;
import net.jloop.rejoice.functions.F_print;
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
import net.jloop.rejoice.functions.O_Divide;
import net.jloop.rejoice.functions.O_Minus;
import net.jloop.rejoice.functions.O_Modulus;
import net.jloop.rejoice.functions.O_Multiply;
import net.jloop.rejoice.functions.O_Plus;
import net.jloop.rejoice.functions.Oabs;
import net.jloop.rejoice.functions.Ochoice;
import net.jloop.rejoice.functions.Oequal_Q_;
import net.jloop.rejoice.functions.F_include;
import net.jloop.rejoice.functions.Omax;
import net.jloop.rejoice.functions.Omin;
import net.jloop.rejoice.functions.F_module;
import net.jloop.rejoice.functions.Oopcase;
import net.jloop.rejoice.functions.Osign;
import net.jloop.rejoice.macros.M_Define;
import net.jloop.rejoice.macros.M_List;
import net.jloop.rejoice.macros.M_MultilineComment;
import net.jloop.rejoice.macros.M_Stack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Runtime {

    private final Interpreter interpreter;
    private final Rewriter rewriter;
    private final Parser parser;
    private final Lexer lexer;
    private final Context context;
    private Stack stack;

    public Runtime(Interpreter interpreter, Rewriter rewriter, Parser parser, Lexer lexer, Context context) {
        this(interpreter, rewriter, parser, lexer, context, new Stack());
    }

    public Runtime(Interpreter interpreter, Rewriter rewriter, Parser parser, Lexer lexer, Context context, Stack stack) {
        this.interpreter = interpreter;
        this.rewriter = rewriter;
        this.parser = parser;
        this.lexer = lexer;
        this.context = context;
        this.stack = stack;
    }

    public void load(String moduleName, InputStream source) {
        load(new Module(moduleName), source);
    }

    public void load(Module module, InputStream source) {
        context.load(module);
        eval(new Input(new InputStreamReader(source)));
    }

    public void eval(Input input) {
        stack = interpreter.interpret(stack, context, rewriter.rewrite(parser.parse(lexer.lex(input)).iterator()));
    }

    public void eval(Function function) {
        stack = function.invoke(stack, context);
    }

    public Context context() {
        return context;
    }

    public Stack stack() {
        return stack;
    }

    public static Runtime create() {

        // Functions
        Module m_native = new Module("native");
        m_native.define("/", new O_Divide());
        m_native.define("-", new O_Minus());
        m_native.define("%", new O_Modulus());
        m_native.define("*", new O_Multiply());
        m_native.define("+", new O_Plus());
        m_native.define("abs", new Oabs());
        m_native.define("app1", new Capp1());
        m_native.define("app2", new Capp2());
        m_native.define("app3", new Capp3());
        m_native.define("b", new Cb());
        m_native.define("choice", new Ochoice());
        m_native.define("cleave", new Ccleave());
        m_native.define("%cons", new F_cons());
        m_native.define("define!", new F_define_E_(true));
        m_native.define("dipd", new Cdipd());
        m_native.define("dipdd", new Cdipdd());
        m_native.define("equal?", new Oequal_Q_());
        m_native.define("i", new Ci());
        m_native.define("%if", new F_if());
        m_native.define("include", new F_include());
        m_native.define("list", new F_list());
        m_native.define("map", new Cmap());
        m_native.define("max", new Omax());
        m_native.define("min", new Omin());
        m_native.define("module", new F_module());
        m_native.define("nullary", new Cnullary());
        m_native.define("opcase", new Oopcase());
        m_native.define("%pop", new F_pop());
        m_native.define("%print", new F_print());
        m_native.define("%roll-down", new F_roll_down());
        m_native.define("%roll-up", new F_roll_up());
        m_native.define("sign", new Osign());
        m_native.define("stack", new F_stack());
        m_native.define("%stack-apply", new F_stack_apply());
        m_native.define("%stack-demote", new F_stack_demote());
        m_native.define("%stack-get", new F_stack_get());
        m_native.define("%stack-peek", new F_stack_peek());
        m_native.define("%stack-pop", new F_stack_pop());
        m_native.define("%stack-promote", new F_stack_promote());
        m_native.define("%stack-push", new F_stack_push());
        m_native.define("%stack-take", new F_stack_take());
        m_native.define("while", new Cwhile());
        m_native.define("x", new Cx());
        m_native.define("y", new Cy());

        // Macros
        // TODO: Scope macros to modules
        Map<String, Macro> macros = new HashMap<>();
        macros.put("(", new M_List());
        macros.put("[", new M_Stack());
        macros.put("define", new M_Define());
        macros.put("/*", new M_MultilineComment());

        // Configure lexer
        Lexer lexer = new Lexer();

        // Configure parser
        Parser parser = new Parser();

        // Configure rewriter
        Rewriter rewriter = new Rewriter(macros);

        // Configure interpreter
        Interpreter interpreter = new Interpreter();

        // Initialize
        Context context = new Context(interpreter);
        context.add(m_native);
        return new Runtime(interpreter, rewriter, parser, lexer, context);
    }
}
