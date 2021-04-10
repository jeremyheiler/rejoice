package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public final class M_Libra implements Macro {

    private final M_MultilineComment comment;
    private final Symbol op_define;
    private final Symbol definitionSeparator;
    private final Symbol functionTerminator;
    private final Symbol macroTerminator;
    private final Symbol listStart;
    private final Symbol listEnd;

    public M_Libra(M_MultilineComment comment, Symbol op_define, Symbol definitionSeparator, Symbol functionTerminator, Symbol macroTerminator, Symbol listStart, Symbol listEnd) {
        this.comment = comment;
        this.op_define = op_define;
        this.definitionSeparator = definitionSeparator;
        this.functionTerminator = functionTerminator;
        this.macroTerminator = macroTerminator;
        this.listStart = listStart;
        this.listEnd = listEnd;
    }

    @Override
    public Iterable<Atom> rewrite(Rewriter rewriter, Iterator<Atom> input) {
        ArrayList<Atom> output = new ArrayList<>();
        while (input.hasNext()) {
            Atom next = input.next();
            if (next.equals(Symbol.of("HIDE"))) {
                throw new RuntimeError("MACRO", "HIDE not yet implemented");
            } else if (next.equals(comment.dispatcher())) {
                comment.rewrite(rewriter, input);
            } else if (next instanceof Symbol) {
                Symbol name = (Symbol) next;
                rewriter.match(input, definitionSeparator);
                output.add(listStart);
                Symbol termination = rewriter.collectInto(output, input, Set.of(functionTerminator, macroTerminator), true);
                output.add(listEnd);
                output.add(new Quote(name));
                output.add(op_define);
                if (termination.equals(macroTerminator)) {
                    break;
                }
            } else {
                throw new RuntimeError("MACRO", "Unexpected atom '" + next.print() + "'");
            }
        }
        return output;
    }
}
