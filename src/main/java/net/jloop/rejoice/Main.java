package net.jloop.rejoice;

import net.jloop.rejoice.languages.Rejoice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;

public class Main {

    private static final HashMap<String, RuntimeFactory> runtimes = new HashMap<>();

    static {
        runtimes.put("rejoice", new Rejoice());
    }

    public static void main(String[] args) {
        System.out.println("Rejoice 0.0.1-alpha");

        String command = args[0];

        if (command.equals("repl")) {
            try {
                Runtime runtime = runtimes.get("rejoice").create();
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        System.out.print("> ");
                        String line = reader.readLine();
                        runtime.eval(new Input(new StringReader(line)));
                    } catch (RuntimeError error) {
                        System.out.println(error.getStage() + " ERROR: " + error.getMessage());
                        if (error.getCause() != null) {
                            error.getCause().printStackTrace();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
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

        if (command.equals("help")) {
            printCommands(System.out);
            System.exit(0);
        }

        if (command.equals("eval")) {
            try {
                Runtime runtime = runtimes.get("rejoice").create();
                runtime.eval(new Input(new StringReader(args[1])));
                runtime.eval(new Input(new StringReader("print!")));
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
}
