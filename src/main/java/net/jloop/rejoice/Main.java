package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Rejoice 0.0.1-alpha");

        String command = args[0];

        if (command.equals("eval")) {

            String eval = null;
            String load = null;

            // TODO(jeremy): If there are no opts or anything after --, accept input from stdin

            if (args.length == 2) {
                eval = args[1];
            } else if (args.length > 2) {
                String opt = null;
                boolean ready = false;
                for (int i = 1; i < args.length; ++i) {
                    String arg = args[i];
                    if (ready) {
                        eval = args[i];
                        break;
                    }
                    if (opt == null) {
                        if (arg.equals("--")) {
                            ready = true;
                        } else if (arg.startsWith("--")) {
                            opt = arg.substring(2);
                        } else {
                            System.err.println("ERROR: All options must begin with '--'");
                            System.exit(1);
                        }
                    } else {
                        switch (opt) {
                            case "load" -> load = args[i]; // TODO(jeremy) Support loading multiple files
                            default -> {
                                System.err.println("ERROR: Unknown option '" + opt + "'");
                                System.exit(1);
                            }
                        }
                        opt = null;
                    }
                }
            } else {
                System.err.println("ERROR: Invalid eval command");
                System.err.println();
                System.err.println("Examples:");
                System.err.println();
                System.err.println("\trejoice eval '1 inc !'");
                System.err.println("\trejoice eval --load foo.rejoice -- 'foo/bar !'");
                System.exit(1);
            }

            Runtime runtime = Runtime.create();
            try {
                loadCore(runtime);
                if (load != null) {
                    runtime.eval(new FileReader(load));
                }
                if (eval != null) {
                    runtime.eval(new StringReader(eval));
                }
                System.exit(0);
            } catch (RuntimeError error) {
                printError(runtime, error);
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

        if (command.equals("repl")) {

            String eval = null;
            String load = null;

            if (args.length > 1) {
                String opt = null;
                for (int i = 1; i < args.length; ++i) {
                    String arg = args[i];
                    if (opt == null) {
                        if (arg.startsWith("--")) {
                            opt = arg.substring(2);
                        } else {
                            System.err.println("ERROR: All options must begin with '--'");
                            System.exit(1);
                        }
                    } else {
                        switch (opt) {
                            case "eval" -> eval = args[i];
                            case "load" -> load = args[i]; // TODO(jeremy) Support loading multiple files
                            default -> {
                                System.err.println("ERROR: Unknown option '" + opt + "'");
                                System.exit(1);
                            }
                        }
                        opt = null;
                    }
                }
            }

            Runtime runtime = Runtime.create();
            try {
                loadCore(runtime);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                if (load != null) {
                    runtime.eval(new FileReader(load));
                }
                if (eval != null) {
                    runtime.eval(new StringReader(eval));
                }
                // TODO(jeremy) Exit when the 'quit' operator is evaluated
                while (true) {
                    try {
                        System.out.print("> ");
                        runtime.eval(new StringReader(reader.readLine()));
                    } catch (RuntimeError error) {
                        printError(runtime, error);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (RuntimeError error) {
                printError(runtime, error);
                System.exit(1);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }

        System.err.println("Error: Unknown command '" + command + "'");
        printCommands(System.err);

        System.exit(1);
    }

    private static void loadCore(Runtime runtime) {
        InputStream input = Main.class.getResourceAsStream("/proto.rejoice");
        if (input == null) {
            throw new RuntimeError("NS", "Could not find proto.rejoice on the classpath");
        }
        runtime.eval(new InputStreamReader(input));
    }

    private static void printError(Runtime runtime, RuntimeError error) {
        System.out.println(error.getStage() + " ERROR: " + error.getMessage());
        for (Symbol symbol : runtime.env().trace().calls()) {
            System.out.println("\t" + symbol.print());
        }
        runtime.env().trace().clear();
    }

    private static void printCommands(PrintStream stream) {
        stream.println();
        stream.println("Commands:");
        stream.println("\teval <program>");
        stream.println("\thelp");
        stream.println("\trepl");
    }
}
