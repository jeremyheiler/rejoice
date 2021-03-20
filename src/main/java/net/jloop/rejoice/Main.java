package net.jloop.rejoice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Rejoice 0.0.1-alpha");

        String command = args[0];

        if (command.equals("repl")) {
            Rejoice rt = new Rejoice();
            Stack main = new Stack();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (true) {
                    System.out.print("> ");
                    String line = reader.readLine();
                    InterpreterResult result = rt.interpret(main, line);
                    main = result.getStack();
                    if (result.getError() != null) {
                        RejoiceError error = result.getError();
                        System.out.println(error.getStage() + " ERROR: " + error.getMessage() + (error.getDetails() == null ? "" : "; " + error.getDetails()));
                    }
                    main.print();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        if (command.equals("help")) {
            printCommands(System.out);
            System.exit(0);
        }

        if (command.equals("eval")) {
            String program = args[1];
            try {
                InterpreterResult result = new Rejoice().interpret(program);
                if (result.getError() == null) {
                    result.getStack().print();
                } else {
                    RejoiceError error = result.getError();
                    System.out.println(error.getStage() + " ERROR: " + result.getError().getMessage());
                }
                System.exit(0);
            } catch (Exception e) {
                System.err.print("Error: ");
                e.printStackTrace();
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
        stream.println("\thelp");
        stream.println("\teval <program>");
    }
}
