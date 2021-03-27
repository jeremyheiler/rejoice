package net.jloop.rejoice;

import net.jloop.rejoice.languages.Joy;
import net.jloop.rejoice.languages.Rejoice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;

public class Main {

    private static final HashMap<String, RuntimeFactory> runtimes = new HashMap<>();

    static {
        runtimes.put("joy", new Joy());
        runtimes.put("rejoice", new Rejoice());
    }

    public static void main(String[] args) {
        System.out.println("Rejoice 0.0.1-alpha");

        String command = args[0];

        if (command.equals("eval")) {

            String eval = null;
            String load = null;
            String mode = "rejoice";

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
                            case "mode" -> mode = args[i];
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
                System.err.println("\trejoice eval --mode joy -- '1 succ put'");
                System.exit(1);
            }

            System.out.println("Mode: " + mode);

            try {
                Runtime runtime = runtimes.get(mode).create();
                if (load != null) {
                    runtime.eval(new Input(new FileReader(load)));
                }
                if (eval != null) {
                    runtime.eval(new Input(new StringReader(eval)));
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
            String mode = "rejoice";

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
                            case "mode" -> mode = args[i];
                            default -> {
                                System.err.println("ERROR: Unknown option '" + opt + "'");
                                System.exit(1);
                            }
                        }
                        opt = null;
                    }
                }
            }

            System.out.println("Mode: " + mode);

            try {
                Runtime runtime = runtimes.get(mode).create();
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                if (load != null) {
                    runtime.eval(new Input(new FileReader(load)));
                }
                if (eval != null) {
                    runtime.eval(new Input(new StringReader(eval)));
                }
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
