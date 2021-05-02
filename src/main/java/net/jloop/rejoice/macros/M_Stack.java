package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class M_Stack implements Macro {

    @Override
    public Iterator<Value> rewrite(Env env, Iterator<Value> input) {
        // Pattern
        List<Value> elements = new ArrayList<>();
        Macro.collect(env, input, elements, Symbol.of("]"));
        // Template
        List<Value> output = new ArrayList<>();
        Collections.reverse(elements);
        elements.stream().map(Value::quote).forEach(output::add);
        output.add(new Int64(elements.size()));
        output.add(Symbol.of("stack"));
        return output.iterator();
    }
}
