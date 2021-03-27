package net.jloop.rejoice;

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
import net.jloop.rejoice.functions.Mdefine;
import net.jloop.rejoice.functions.Mlist;
import net.jloop.rejoice.functions.O_minus;
import net.jloop.rejoice.functions.O_mod;
import net.jloop.rejoice.functions.O_plus;
import net.jloop.rejoice.functions.O_divide;
import net.jloop.rejoice.functions.O_multiply;
import net.jloop.rejoice.functions.Oabs;
import net.jloop.rejoice.functions.Ochoice;
import net.jloop.rejoice.functions.Odup;
import net.jloop.rejoice.functions.Odupd;
import net.jloop.rejoice.functions.Oequal_Q_;
import net.jloop.rejoice.functions.Omax;
import net.jloop.rejoice.functions.Omin;
import net.jloop.rejoice.functions.Oopcase;
import net.jloop.rejoice.functions.Opop;
import net.jloop.rejoice.functions.Opopd;
import net.jloop.rejoice.functions.O_E_;
import net.jloop.rejoice.functions.Orolldown;
import net.jloop.rejoice.functions.Orollup;
import net.jloop.rejoice.functions.Osign;
import net.jloop.rejoice.functions.Oswap;
import net.jloop.rejoice.functions.Oswapd;
import net.jloop.rejoice.types.Symbol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Rejoice 0.0.1-alpha");

        String command = args[0];

        if (command.equals("repl")) {
            Runtime rt = new Runtime(new Interpreter(library));
            try {
                rt.init();
            } catch (RuntimeError error) {
                System.out.println(error.getStage() + " ERROR: " + error.getMessage());
                if (error.getCause() != null) {
                    error.getCause().printStackTrace();
                }
                System.exit(1);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                try {
                    System.out.print("> ");
                    String line = reader.readLine();
                    rt.eval(new Input(new StringReader(line)));
                } catch (RuntimeError error) {
                    System.out.println(error.getStage() + " ERROR: " + error.getMessage());
                    if (error.getCause() != null) {
                        error.getCause().printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (command.equals("help")) {
            printCommands(System.out);
            System.exit(0);
        }

        if (command.equals("eval")) {
            Input input = new Input(new StringReader(args[1]));
            Runtime rt = new Runtime(new Interpreter(library));
            try {
                rt.init();
                rt.eval(input);
                rt.eval(new Input(new StringReader("print!")));
                System.exit(0);
            } catch (RuntimeError error) {
                System.out.println(error.getStage() + " ERROR: " + error.getMessage());
                if (error.getCause() != null) {
                    error.getCause().printStackTrace();
                }
                System.exit(1);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }

        System.err.println("Error: Unknown command '" + command + "'.");
        printCommands(System.err);

        System.exit(1);
    }

    private static void printCommands(PrintStream stream) {
        stream.println();
        stream.println("Commands:");
        stream.println("\teval <program>");
        stream.println("\thelp");
        stream.println("\trepl");
    }

    private static final Library library = new Library();

    static {
        // Operators
        library.define(Symbol.of("/"), new O_divide());
        library.define(Symbol.of("!"), new O_E_());
        library.define(Symbol.of("-"), new O_minus());
        library.define(Symbol.of("%"), new O_mod());
        library.define(Symbol.of("*"), new O_multiply());
        library.define(Symbol.of("+"), new O_plus());
        library.define(Symbol.of("abs"), new Oabs());
        library.define(Symbol.of("choice"), new Ochoice());
        library.define(Symbol.of("dup"), new Odup());
        library.define(Symbol.of("dupd"), new Odupd());
        library.define(Symbol.of("equal?"), new Oequal_Q_());
        library.define(Symbol.of("max"), new Omax());
        library.define(Symbol.of("min"), new Omin());
        library.define(Symbol.of("opcase"), new Oopcase());
        library.define(Symbol.of("pop"), new Opop());
        library.define(Symbol.of("popd"), new Opopd());
        library.define(Symbol.of("rolldown"), new Orolldown());
        library.define(Symbol.of("rollup"), new Orollup());
        library.define(Symbol.of("sign"), new Osign());
        library.define(Symbol.of("swap"), new Oswap());
        library.define(Symbol.of("swapd"), new Oswapd());

        // Combinators
        library.define(Symbol.of("app1"), new Capp1());
        library.define(Symbol.of("app2"), new Capp2());
        library.define(Symbol.of("app3"), new Capp3());
        library.define(Symbol.of("b"), new Cb());
        library.define(Symbol.of("cleave"), new Ccleave());
        library.define(Symbol.of("dip"), new Cdip());
        library.define(Symbol.of("dipd"), new Cdipd());
        library.define(Symbol.of("dipdd"), new Cdipdd());
        library.define(Symbol.of("i"), new Ci());
        library.define(Symbol.of("ifte"), new Cifte());
        library.define(Symbol.of("map"), new Cmap());
        library.define(Symbol.of("nullary"), new Cnullary());
        library.define(Symbol.of("while"), new Cwhile());
        library.define(Symbol.of("x"), new Cx());
        library.define(Symbol.of("y"), new Cy());

        // Macros
        library.define(Symbol.of("["), new Mlist(Symbol.of("]")));
        library.define(Symbol.of("define"), new Mdefine(Symbol.of(":"), Symbol.of(";")));
    }
}
