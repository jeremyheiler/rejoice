package net.jloop.rejoice.languages;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Lexer;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Module;
import net.jloop.rejoice.Parser;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.Runtime;
import net.jloop.rejoice.RuntimeFactory;
import net.jloop.rejoice.functions.Capp1;
import net.jloop.rejoice.functions.Capp2;
import net.jloop.rejoice.functions.Capp3;
import net.jloop.rejoice.functions.Cb;
import net.jloop.rejoice.functions.Ccleave;
import net.jloop.rejoice.functions.Cdip;
import net.jloop.rejoice.functions.Cdipd;
import net.jloop.rejoice.functions.Cdipdd;
import net.jloop.rejoice.functions.Ci;
import net.jloop.rejoice.functions.Cifte;
import net.jloop.rejoice.functions.Cmap;
import net.jloop.rejoice.functions.Cnullary;
import net.jloop.rejoice.functions.Cwhile;
import net.jloop.rejoice.functions.Cx;
import net.jloop.rejoice.functions.Cy;
import net.jloop.rejoice.functions.O_Divide;
import net.jloop.rejoice.functions.O_Minus;
import net.jloop.rejoice.functions.O_Modulus;
import net.jloop.rejoice.functions.O_Multiply;
import net.jloop.rejoice.functions.O_Plus;
import net.jloop.rejoice.functions.O_PrintStack;
import net.jloop.rejoice.functions.Oabs;
import net.jloop.rejoice.functions.Ochoice;
import net.jloop.rejoice.functions.Odefine_E_;
import net.jloop.rejoice.functions.Odup;
import net.jloop.rejoice.functions.Odupd;
import net.jloop.rejoice.functions.Oequal_Q_;
import net.jloop.rejoice.functions.Oinclude;
import net.jloop.rejoice.functions.Olist;
import net.jloop.rejoice.functions.Omax;
import net.jloop.rejoice.functions.Omin;
import net.jloop.rejoice.functions.Omodule;
import net.jloop.rejoice.functions.Oopcase;
import net.jloop.rejoice.functions.Opop;
import net.jloop.rejoice.functions.Opopd;
import net.jloop.rejoice.functions.Orolldown;
import net.jloop.rejoice.functions.Orollup;
import net.jloop.rejoice.functions.Osign;
import net.jloop.rejoice.functions.Oswap;
import net.jloop.rejoice.functions.Oswapd;
import net.jloop.rejoice.macros.M_Define;
import net.jloop.rejoice.macros.M_List;
import net.jloop.rejoice.macros.M_MultilineComment;
import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Map;

public final class Rejoice implements RuntimeFactory {

    @Override
    public Runtime create() {

        // Operators and Combinators
        Module m_native = new Module("native");
        m_native.define(Symbol.of("/"), new O_Divide());
        m_native.define(Symbol.of("!"), new O_PrintStack());
        m_native.define(Symbol.of("-"), new O_Minus());
        m_native.define(Symbol.of("%"), new O_Modulus());
        m_native.define(Symbol.of("*"), new O_Multiply());
        m_native.define(Symbol.of("+"), new O_Plus());
        m_native.define(Symbol.of("abs"), new Oabs());
        m_native.define(Symbol.of("app1"), new Capp1());
        m_native.define(Symbol.of("app2"), new Capp2());
        m_native.define(Symbol.of("app3"), new Capp3());
        m_native.define(Symbol.of("b"), new Cb());
        m_native.define(Symbol.of("choice"), new Ochoice());
        m_native.define(Symbol.of("cleave"), new Ccleave());
        m_native.define(Symbol.of("define!"), new Odefine_E_(true));
        m_native.define(Symbol.of("dip"), new Cdip());
        m_native.define(Symbol.of("dipd"), new Cdipd());
        m_native.define(Symbol.of("dipdd"), new Cdipdd());
        m_native.define(Symbol.of("dup"), new Odup());
        m_native.define(Symbol.of("dupd"), new Odupd());
        m_native.define(Symbol.of("equal?"), new Oequal_Q_());
        m_native.define(Symbol.of("i"), new Ci());
        m_native.define(Symbol.of("ifte"), new Cifte());
        m_native.define(Symbol.of("include"), new Oinclude());
        m_native.define(Symbol.of("list"), new Olist());
        m_native.define(Symbol.of("map"), new Cmap());
        m_native.define(Symbol.of("max"), new Omax());
        m_native.define(Symbol.of("min"), new Omin());
        m_native.define(Symbol.of("module"), new Omodule());
        m_native.define(Symbol.of("nullary"), new Cnullary());
        m_native.define(Symbol.of("opcase"), new Oopcase());
        m_native.define(Symbol.of("pop"), new Opop());
        m_native.define(Symbol.of("popd"), new Opopd());
        m_native.define(Symbol.of("rolldown"), new Orolldown());
        m_native.define(Symbol.of("rollup"), new Orollup());
        m_native.define(Symbol.of("sign"), new Osign());
        m_native.define(Symbol.of("swap"), new Oswap());
        m_native.define(Symbol.of("swapd"), new Oswapd());
        m_native.define(Symbol.of("while"), new Cwhile());
        m_native.define(Symbol.of("x"), new Cx());
        m_native.define(Symbol.of("y"), new Cy());

        // Macros
        Map<Symbol, Macro> macros = new HashMap<>();
        macros.put(Symbol.of("["), new M_List(Symbol.of("]"), Symbol.of("list")));
        macros.put(Symbol.of("define"), new M_Define(Symbol.of("define!"), Symbol.of(":"), Symbol.of(";"), Symbol.of("["), Symbol.of("]")));
        macros.put(Symbol.of("/*"), new M_MultilineComment(Symbol.of("/*"), Symbol.of("*/")));

        // Configure lexer
        Lexer lexer = new Lexer('\\', true);

        // Configure parser
        Parser parser = new Parser();

        // Configure rewriter
        Rewriter rewriter = new Rewriter(macros);

        // Configure interpreter
        Interpreter interpreter = new Interpreter();

        // Initialize
        Context context = new Context(interpreter);
        Runtime runtime = new Runtime("Rejoice", interpreter, rewriter, parser, lexer, context);
        Module core = new Module("core").include(m_native);
        runtime.load(core, Runtime.class.getResourceAsStream("/core.rejoice"));
        return runtime;
    }
}
