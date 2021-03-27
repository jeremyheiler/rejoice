package net.jloop.rejoice;

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
        library.define(Symbol.of("/"), Operators::_divide);
        library.define(Symbol.of("="), Operators::_equal);
        library.define(Symbol.of("-"), Operators::_minus);
        library.define(Symbol.of("%"), Operators::_mod);
        library.define(Symbol.of("*"), Operators::_multiply);
        library.define(Symbol.of("+"), Operators::_plus);
        library.define(Symbol.of("abs"), Operators::abs);
        library.define(Symbol.of("choice"), Operators::choice);
        library.define(Symbol.of("dup"), Operators::dup);
        library.define(Symbol.of("dupd"), Operators::dupd);
        library.define(Symbol.of("min"), Operators::min);
        library.define(Symbol.of("max"), Operators::max);
        library.define(Symbol.of("opcase"), Operators::opcase);
        library.define(Symbol.of("pop"), Operators::pop);
        library.define(Symbol.of("popd"), Operators::popd);
        library.define(Symbol.of("pred"), Operators::pred);
        library.define(Symbol.of("print!"), Operators::print_BANG_);
        library.define(Symbol.of("rolldown"), Operators::rolldown);
        library.define(Symbol.of("rollup"), Operators::rollup);
        library.define(Symbol.of("sign"), Operators::sign);
        library.define(Symbol.of("succ"), Operators::succ);
        library.define(Symbol.of("swap"), Operators::swap);
        library.define(Symbol.of("swapd"), Operators::swapd);

        // Combinators
        library.define(Symbol.of("app1"), Combinators::app1);
        library.define(Symbol.of("app2"), Combinators::app2);
        library.define(Symbol.of("app3"), Combinators::app3);
        library.define(Symbol.of("b"), Combinators::b);
        library.define(Symbol.of("cleave"), Combinators::cleave);
        library.define(Symbol.of("dip"), Combinators::dip);
        library.define(Symbol.of("dipd"), Combinators::dipd);
        library.define(Symbol.of("dipdd"), Combinators::dipdd);
        library.define(Symbol.of("i"), Combinators::i);
        library.define(Symbol.of("ifte"), Combinators::ifte);
        library.define(Symbol.of("map"), Combinators::map);
        library.define(Symbol.of("nullary"), Combinators::nullary);
        library.define(Symbol.of("while"), Combinators::_while);
        library.define(Symbol.of("x"), Combinators::x);
        library.define(Symbol.of("y"), Combinators::y);

        // Macros
        library.define(Symbol.of("["), new Macros.ListLiteral(Symbol.of("]")));
        library.define(Symbol.of("define"), new Macros.Define(Symbol.of(":"), Symbol.of(";")));
    }
}
