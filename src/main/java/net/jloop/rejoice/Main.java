package net.jloop.rejoice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Rejoice 0.0.1-alpha");

        String command = args[0];

        if (command.equals("repl")) {
            Stack stack = new Stack();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Interpreter interpreter = new Interpreter(library);
            while (true) {
                System.out.print("> ");
                try {
                    String line = reader.readLine();
                    Input input = new Input(new StringReader(line));
                    stack = interpreter.interpret(stack, input);
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
            Stack stack = new Stack();
            Input input = new Input(new StringReader(args[1]));
            Interpreter interpreter = new Interpreter(library);
            try {
                Stack result;
                while ((result = interpreter.interpret(stack, input)) != null) {
                    stack = result;
                }
                stack.print();
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
            System.exit(0);
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
        Library.Definitions<Operator> operators = library.operators();
        operators.define("/", Operators::_divide);
        operators.define("=", Operators::_equal);
        operators.define("-", Operators::_minus);
        operators.define("%", Operators::_mod);
        operators.define("*", Operators::_multiply);
        operators.define("+", Operators::_plus);
        operators.define("abs", Operators::abs);
        operators.define("choice", Operators::choice);
        operators.define("dup", Operators::dup);
        operators.define("dupd", Operators::dupd);
        operators.define("min", Operators::min);
        operators.define("max", Operators::max);
        operators.define("opcase", Operators::opcase);
        operators.define("pop", Operators::pop);
        operators.define("popd", Operators::popd);
        operators.define("popop", Operators::popop);
        operators.define("pred", Operators::pred);
        operators.define("print!", Operators::print_BANG_);
        operators.define("rolldown", Operators::rolldown);
        operators.define("rollup", Operators::rollup);
        operators.define("sign", Operators::sign);
        operators.define("succ", Operators::succ);
        operators.define("swap", Operators::swap);
        operators.define("swapd", Operators::swapd);

        Library.Definitions<Combinator> combinators = library.combinators();
        combinators.define("app1", Combinators::app1);
        combinators.define("app2", Combinators::app2);
        combinators.define("app3", Combinators::app3);
        combinators.define("b", Combinators::b);
        combinators.define("cleave", Combinators::cleave);
        combinators.define("dip", Combinators::dip);
        combinators.define("dipd", Combinators::dipd);
        combinators.define("dipdd", Combinators::dipdd);
        combinators.define("i", Combinators::i);
        combinators.define("ifte", Combinators::ifte);
        combinators.define("map", Combinators::map);
        combinators.define("nullary", Combinators::nullary);
        combinators.define("while", Combinators::_while);
        combinators.define("x", Combinators::x);
        combinators.define("y", Combinators::y);

        Library.Definitions<Macro> macros = library.macros();
        macros.define("[", new Macros.ListLiteral(Symbol.of("]")));
    }
}
