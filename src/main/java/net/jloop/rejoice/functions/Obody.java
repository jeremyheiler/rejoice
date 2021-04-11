package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Definition;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

import java.util.Optional;

// Function -> List

public final class Obody implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        Symbol name = stack.consume(Symbol.class);
        Optional<Definition> definition = context.current().get(context, name);
        if (definition.isPresent() && (definition.get().function() instanceof Interpreted)) {
            return stack.push(((Interpreted) definition.get().function()).body());
        } else {
            return stack.push(new List());
        }
    }
}
