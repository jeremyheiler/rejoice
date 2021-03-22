package net.jloop.rejoice;

public final class Macros {

    public static final class ListLiteral implements Macro {

        private final Symbol terminator;

        public ListLiteral(Symbol terminator) {
            this.terminator = terminator;
        }

        @Override
        public Stack evaluate(Stack stack, Interpreter interpreter, Interpreter.Next next) {
            Stack syntax = new Stack();

            Atom atom;
            while ((atom = next.next()) != null) {
                if (atom instanceof Symbol && atom.equals(terminator)) {
                    return stack.push(List.from(syntax));
                }
                syntax = interpreter.interpret(syntax, atom, next, Interpreter.Mode.Macro);
            }
            throw new RuntimeError("MACRO", "Unexpected EOF; Run-on list");
        }
    }
}
