package net.jloop.rejoice.languages;

import net.jloop.rejoice.Function;
import net.jloop.rejoice.Interpreter;
import net.jloop.rejoice.Lexer;
import net.jloop.rejoice.LexerRule;
import net.jloop.rejoice.Macro;
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
import net.jloop.rejoice.functions.O_E_;
import net.jloop.rejoice.functions.O_Minus;
import net.jloop.rejoice.functions.O_Modulus;
import net.jloop.rejoice.functions.O_Multiply;
import net.jloop.rejoice.functions.O_Plus;
import net.jloop.rejoice.functions.Oabs;
import net.jloop.rejoice.functions.Ochoice;
import net.jloop.rejoice.functions.Odefine_E_;
import net.jloop.rejoice.functions.Odup;
import net.jloop.rejoice.functions.Odupd;
import net.jloop.rejoice.functions.Oequal_Q_;
import net.jloop.rejoice.functions.Olist;
import net.jloop.rejoice.functions.Omax;
import net.jloop.rejoice.functions.Omin;
import net.jloop.rejoice.functions.Oopcase;
import net.jloop.rejoice.functions.Opop;
import net.jloop.rejoice.functions.Opopd;
import net.jloop.rejoice.functions.Orolldown;
import net.jloop.rejoice.functions.Orollup;
import net.jloop.rejoice.functions.Osign;
import net.jloop.rejoice.functions.Oswap;
import net.jloop.rejoice.functions.Oswapd;
import net.jloop.rejoice.macros.M_List;
import net.jloop.rejoice.macros.M_MultilineComment;
import net.jloop.rejoice.macros.Mdefine;
import net.jloop.rejoice.types.Symbol;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

import static net.jloop.rejoice.Lexer.EOF;

public final class Rejoice implements RuntimeFactory {

    @Override
    public Runtime create() {

        // Operators and Combinators
        Map<Symbol, Function> functions = new HashMap<>();
        functions.put(Symbol.of("/"), new O_Divide());
        functions.put(Symbol.of("!"), new O_E_());
        functions.put(Symbol.of("-"), new O_Minus());
        functions.put(Symbol.of("%"), new O_Modulus());
        functions.put(Symbol.of("*"), new O_Multiply());
        functions.put(Symbol.of("+"), new O_Plus());
        functions.put(Symbol.of("abs"), new Oabs());
        functions.put(Symbol.of("app1"), new Capp1());
        functions.put(Symbol.of("app2"), new Capp2());
        functions.put(Symbol.of("app3"), new Capp3());
        functions.put(Symbol.of("b"), new Cb());
        functions.put(Symbol.of("choice"), new Ochoice());
        functions.put(Symbol.of("cleave"), new Ccleave());
        functions.put(Symbol.of("define!"), new Odefine_E_(functions));
        functions.put(Symbol.of("dip"), new Cdip());
        functions.put(Symbol.of("dipd"), new Cdipd());
        functions.put(Symbol.of("dipdd"), new Cdipdd());
        functions.put(Symbol.of("dup"), new Odup());
        functions.put(Symbol.of("dupd"), new Odupd());
        functions.put(Symbol.of("equal?"), new Oequal_Q_());
        functions.put(Symbol.of("i"), new Ci());
        functions.put(Symbol.of("ifte"), new Cifte());
        functions.put(Symbol.of("list"), new Olist());
        functions.put(Symbol.of("map"), new Cmap());
        functions.put(Symbol.of("max"), new Omax());
        functions.put(Symbol.of("min"), new Omin());
        functions.put(Symbol.of("nullary"), new Cnullary());
        functions.put(Symbol.of("opcase"), new Oopcase());
        functions.put(Symbol.of("pop"), new Opop());
        functions.put(Symbol.of("popd"), new Opopd());
        functions.put(Symbol.of("rolldown"), new Orolldown());
        functions.put(Symbol.of("rollup"), new Orollup());
        functions.put(Symbol.of("sign"), new Osign());
        functions.put(Symbol.of("swap"), new Oswap());
        functions.put(Symbol.of("swapd"), new Oswapd());
        functions.put(Symbol.of("while"), new Cwhile());
        functions.put(Symbol.of("x"), new Cx());
        functions.put(Symbol.of("y"), new Cy());

        // Macros
        Map<Symbol, Macro> macros = new HashMap<>();
        macros.put(Symbol.of("["), new M_List(Symbol.of("]"), Symbol.of("list")));
        macros.put(Symbol.of("define"), new Mdefine(Symbol.of("define!"), Symbol.of("list"), Symbol.of(":"), Symbol.of(";")));
        macros.put(Symbol.of("/*"), new M_MultilineComment(Symbol.of("*/")));

        LexerRule comment = new LexerRule() {
            @Override
            public int dispatcher() {
                return '/';
            }

            @Override
            public Lexer.Token lex(PushbackReader reader) throws IOException {
                int c;
                if ((c = reader.read()) != EOF) {
                    if (c == '/') {
                        StringBuilder buf = new StringBuilder();
                        int d;
                        while ((d = reader.read()) != EOF) {
                            if (d == '\r') {
                                int f;
                                if ((f = reader.read()) != EOF && f != '\n') {
                                    reader.unread(f);
                                }
                                break;
                            } else if (d == '\n') {
                                break;
                            } else {
                                buf.append(d);
                            }
                        }
                        return Lexer.Token.of(Lexer.Token.Type.LineComment, buf.toString());
                    } else if (c >= '!' && c <= 'z' && c != '[' && c != ']') {
                        // Consume a symbol that begins with '/' but not `//`
                        StringBuilder buf = new StringBuilder().append("/").append((char) c);
                        int d;
                        while ((d = reader.read()) != EOF) {
                            if (d >= '!' && d <= 'z' && d != '[' && d != ']') {
                                buf.append((char) d);
                            } else {
                                reader.unread(d);
                                break;
                            }
                        }
                        return Lexer.Token.of(Lexer.Token.Type.Symbol, buf.toString());
                    } else {
                        // Push back c for the next lex call
                        reader.unread(c);
                        return Lexer.Token.of(Lexer.Token.Type.Symbol, "/");
                    }
                } else {
                    return Lexer.Token.of(Lexer.Token.Type.Symbol, "/");
                }
            }
        };

        // Configure lexer
        Lexer lexer = new Lexer(comment);

        // Configure parser
        Parser parser = new Parser();

        // Configure expander
        Rewriter expander = new Rewriter(macros);

        // Configure interpreter
        Interpreter interpreter = new Interpreter(functions);

        // Initialize
        Runtime runtime = new Runtime("Rejoice", interpreter, expander, parser, lexer);
        runtime.load(Runtime.class.getResourceAsStream("/core.rejoice"));
        return runtime;
    }
}
