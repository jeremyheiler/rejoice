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
import net.jloop.rejoice.functions.Obody;
import net.jloop.rejoice.functions.Ochoice;
import net.jloop.rejoice.functions.Ocurrent_module;
import net.jloop.rejoice.functions.Odefine_E_;
import net.jloop.rejoice.functions.Odup;
import net.jloop.rejoice.functions.Odupd;
import net.jloop.rejoice.functions.Oequal_Q_;
import net.jloop.rejoice.functions.Oinclude;
import net.jloop.rejoice.functions.Ointern;
import net.jloop.rejoice.functions.Olist;
import net.jloop.rejoice.functions.Omax;
import net.jloop.rejoice.functions.Omin;
import net.jloop.rejoice.functions.Omodule;
import net.jloop.rejoice.functions.Onull;
import net.jloop.rejoice.functions.Oopcase;
import net.jloop.rejoice.functions.Opop;
import net.jloop.rejoice.functions.Opopd;
import net.jloop.rejoice.functions.Oputchars;
import net.jloop.rejoice.functions.Orolldown;
import net.jloop.rejoice.functions.Orollup;
import net.jloop.rejoice.functions.Osign;
import net.jloop.rejoice.functions.Oswap;
import net.jloop.rejoice.functions.Oswapd;
import net.jloop.rejoice.functions.Oswons;
import net.jloop.rejoice.macros.M_Libra;
import net.jloop.rejoice.macros.M_List;
import net.jloop.rejoice.macros.M_MultilineComment;
import net.jloop.rejoice.types.Symbol;

import java.util.HashMap;
import java.util.Map;

public class Joy implements RuntimeFactory {

    @Override
    public Runtime create() {

        // Operators and Combinators
        Module core = new Module("core");
        core.define(Symbol.of("rejoice$list"), new Olist());
        core.define(Symbol.of("rejoice$define!"), new Odefine_E_(true));
        core.define(Symbol.of("rejoice$define-private!"), new Odefine_E_(false));
        core.define(Symbol.of("rejoice$current-module"), new Ocurrent_module());
        core.define(Symbol.of("rejoice$in-module"), new Omodule());
        core.define(Symbol.of("rejoice$include"), new Oinclude());
        core.define(Symbol.of("/"), new O_Divide());
        core.define(Symbol.of("-"), new O_Minus());
        core.define(Symbol.of("%"), new O_Modulus());
        core.define(Symbol.of("*"), new O_Multiply());
        core.define(Symbol.of("+"), new O_Plus());
        core.define(Symbol.of("="), new Oequal_Q_());
        core.define(Symbol.of("."), new O_PrintStack());
        core.define(Symbol.of("abs"), new Oabs());
        core.define(Symbol.of("app1"), new Capp1());
        core.define(Symbol.of("app2"), new Capp2());
        core.define(Symbol.of("app3"), new Capp3());
        core.define(Symbol.of("b"), new Cb());
        core.define(Symbol.of("body"), new Obody());
        core.define(Symbol.of("choice"), new Ochoice());
        core.define(Symbol.of("cleave"), new Ccleave());
        core.define(Symbol.of("dip"), new Cdip());
        core.define(Symbol.of("dipdd"), new Cdipdd());
        core.define(Symbol.of("dup"), new Odup());
        core.define(Symbol.of("dupd"), new Odupd());
        core.define(Symbol.of("i"), new Ci());
        core.define(Symbol.of("ifte"), new Cifte());
        core.define(Symbol.of("intern"), new Ointern());
        core.define(Symbol.of("map"), new Cmap());
        core.define(Symbol.of("max"), new Omax());
        core.define(Symbol.of("min"), new Omin());
        core.define(Symbol.of("null"), new Onull());
        core.define(Symbol.of("nullary"), new Cnullary());
        core.define(Symbol.of("opcase"), new Oopcase());
        core.define(Symbol.of("pop"), new Opop());
        core.define(Symbol.of("popd"), new Opopd());
        core.define(Symbol.of("putchars"), new Oputchars());
        core.define(Symbol.of("rolldown"), new Orolldown());
        core.define(Symbol.of("rollup"), new Orollup());
        core.define(Symbol.of("sign"), new Osign());
        core.define(Symbol.of("swap"), new Oswap());
        core.define(Symbol.of("swapd"), new Oswapd());
        core.define(Symbol.of("swons"), new Oswons());
        core.define(Symbol.of("while"), new Cwhile());
        core.define(Symbol.of("x"), new Cx());
        core.define(Symbol.of("y"), new Cy());

        // Macros
        Map<Symbol, Macro> macros = new HashMap<>();
        macros.put(Symbol.of("["), new M_List(Symbol.of("]"), Symbol.of("rejoice$list")));
        macros.put(Symbol.of("(*"), new M_MultilineComment(Symbol.of("(*"), Symbol.of("*)")));
        macros.put(Symbol.of("LIBRA"), new M_Libra(
                new M_MultilineComment(Symbol.of("(*"), Symbol.of("*)")),
                Symbol.of("rejoice$define!"),
                Symbol.of("rejoice$define-private!"),
                Symbol.of("rejoice$current-module"),
                Symbol.of("rejoice$in-module"),
                Symbol.of("rejoice$include"),
                Symbol.of("=="),
                Symbol.of(";"),
                Symbol.of("."),
                Symbol.of("HIDE"),
                Symbol.of("END"),
                Symbol.of("["),
                Symbol.of("]")));

        // Configure lexer
        Lexer lexer = new Lexer('\'', false);

        // Configure parser
        Parser parser = new Parser();

        // Configure rewriter
        Rewriter rewriter = new Rewriter(macros);

        // Configure interpreter
        Interpreter interpreter = new Interpreter();

        // Initialize
        Context context = new Context(interpreter);
        return new Runtime("Joy", interpreter, rewriter, parser, lexer, context);
    }
}
