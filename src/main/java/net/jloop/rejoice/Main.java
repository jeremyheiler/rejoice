package net.jloop.rejoice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

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

            try {
                Runtime runtime = Runtime.create();
                runtime.context().load(new Module("user").require(runtime.context().get("core")));
                if (load != null) {
                    runtime.eval(new Input(new FileReader(load)));
                }
                if (eval != null) {
                    runtime.eval(new Input(eval));
                }
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

            try {
                Runtime runtime = Runtime.create();
                runtime.context().load(new Module("user").require(runtime.context().get("core")));
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                if (load != null) {
                    runtime.eval(new Input(new FileReader(load)));
                }
                if (eval != null) {
                    runtime.eval(new Input(eval));
                }
                // TODO(jeremy) Exit when the 'quit' operator is evaluated
                while (true) {
                    try {
                        System.out.print("> ");
                        String line = reader.readLine();
                        runtime.eval(new Input(line));
                    } catch (RuntimeError error) {
                        System.out.println(error.getStage() + " ERROR: " + error.getMessage());
                        error.printStackTrace(System.out);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (RuntimeError error) {
                System.out.println(error.getStage() + " ERROR: " + error.getMessage());
                error.printStackTrace(System.out);
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

    private static void printCommands(PrintStream stream) {
        stream.println();
        stream.println("Commands:");
        stream.println("\teval <program>");
        stream.println("\thelp");
        stream.println("\trepl");
    }
}
