package net.jloop.rejoice.macros;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Rewriter;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Str;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public final class M_Libra implements Macro {

    private final M_MultilineComment comment;
    private final Symbol op_define;
    private final Symbol op_define_private;
    private final Symbol op_current_module;
    private final Symbol op_in_module;
    private final Symbol op_include;
    private final Symbol definitionSeparator;
    private final Symbol functionTerminator;
    private final Symbol libraTerminator;
    private final Symbol hide;
    private final Symbol hideTerminator;
    private final Symbol listStart;
    private final Symbol listEnd;

    public M_Libra(M_MultilineComment comment,
                   Symbol op_define,
                   Symbol op_define_private,
                   Symbol op_current_module,
                   Symbol op_in_module,
                   Symbol op_include,
                   Symbol definitionSeparator,
                   Symbol functionTerminator,
                   Symbol libraTerminator,
                   Symbol hide,
                   Symbol hideTerminator,
                   Symbol listStart,
                   Symbol listEnd) {
        this.comment = comment;
        this.op_define = op_define;
        this.op_define_private = op_define_private;
        this.op_current_module = op_current_module;
        this.op_in_module = op_in_module;
        this.op_include = op_include;
        this.definitionSeparator = definitionSeparator;
        this.functionTerminator = functionTerminator;
        this.libraTerminator = libraTerminator;
        this.hide = hide;
        this.hideTerminator = hideTerminator;
        this.listStart = listStart;
        this.listEnd = listEnd;
    }

    @Override
    public Iterable<Atom> rewrite(Rewriter rewriter, Iterator<Atom> input) {
        ArrayList<Atom> output = new ArrayList<>();
        Str moduleName = new Str("JOY-" + UUID.randomUUID());
        output.add(op_current_module);
        output.add(moduleName);
        output.add(op_in_module);
        while (input.hasNext()) {
            Atom next = input.next();
            if (next.equals(libraTerminator)) {
                break;
            } else if (next.equals(hide)) {
                while (input.hasNext()) {
                    Symbol name = (Symbol) next;
                    rewriter.match(input, definitionSeparator);
                    output.add(listStart);
                    Symbol termination = rewriter.collectInto(output, input, Set.of(functionTerminator, hideTerminator), true);
                    output.add(listEnd);
                    output.add(new Quote(name));
                    output.add(op_define_private);
                    if (termination.equals(hideTerminator)) {
                        break;
                    }
                }
            } else if (next.equals(comment.dispatcher())) {
                comment.rewrite(rewriter, input);
            } else if (next instanceof Symbol) {
                Symbol name = (Symbol) next;
                rewriter.match(input, definitionSeparator);
                output.add(listStart);
                Symbol termination = rewriter.collectInto(output, input, Set.of(functionTerminator, libraTerminator), true);
                output.add(listEnd);
                output.add(new Quote(name));
                output.add(op_define);
                if (termination.equals(libraTerminator)) {
                    break;
                }
            } else {
                throw new RuntimeError("MACRO", "Unexpected atom '" + next.print() + "'");
            }
        }
        output.add(op_in_module);
        output.add(moduleName);
        output.add(op_include);
        return output;
    }
}
