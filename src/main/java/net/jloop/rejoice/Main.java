package net.jloop.rejoice;

import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Stack;

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

            Runtime runtime = Runtime.create();
            try {
                initCore(runtime);
                initUser(runtime);
                if (load != null) {
                    runtime.eval(new Input(new FileReader(load)));
                }
                if (eval != null) {
                    runtime.eval(new Input(eval));
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
                initCore(runtime);
                initUser(runtime);
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
                        System.out.print(runtime.context().active().name() + "> ");
                        String line = reader.readLine();
                        runtime.eval(new Input(line));
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

    private static void initCore(Runtime runtime) {
        Module internal = runtime.context().modules().resolve("internal");
        Module core = new Module("core");
        core.include(internal);
        core.defineType("bool", null);
        core.defineType("char", null);
        core.defineType("i64", null);
        core.defineType("list", List::new);
        core.defineType("stack", Stack::new);
        core.defineType("string", null);
        core.defineType("type", null);
        runtime.load(core, Runtime.class.getResourceAsStream("/core.rejoice"));
        runtime.load(Runtime.class.getResourceAsStream("/list.rejoice"));
        runtime.load(Runtime.class.getResourceAsStream("/stack.rejoice"));
    }

    private static void initUser(Runtime runtime) {
        Module user = new Module("user");
        user.require(runtime.context().modules().resolve("core"));
        runtime.context().modules().add(user);
        runtime.context().activate(user);
    }

    private static void printError(Runtime runtime, RuntimeError error) {
        System.out.println(error.getStage() + " ERROR: " + error.getMessage());
        for (Trace.Call call : runtime.context().trace().calls()) {
            System.out.print("\t" + call.fullyQualifiedName());
            for (String include : call.includes()) {
                System.out.print(" < " + include);
            }
            System.out.println();
        }
        System.out.println("\t" + runtime.context().active().name());
        runtime.context().trace().clear();
    }

    private static void printCommands(PrintStream stream) {
        stream.println();
        stream.println("Commands:");
        stream.println("\teval <program>");
        stream.println("\thelp");
        stream.println("\trepl");
    }
}
