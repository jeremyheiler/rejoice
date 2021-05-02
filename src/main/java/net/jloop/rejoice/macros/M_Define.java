package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Macro;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class M_Define implements Macro {

    @Override
    public Iterator<Value> rewrite(Env env, Iterator<Value> input) {
        // Pattern
        Symbol name = Macro.match(input, Symbol.class);
        Macro.match(input, Symbol.of(":"));
        List<Value> body = new ArrayList<>();
        Macro.collect(env, input, body, Symbol.of(";"));
        // Template
        List<Value> output = new ArrayList<>();
        output.add(Symbol.of("("));
        output.addAll(body);
        output.add(Symbol.of(")"));
        output.add(name.quote());
        output.add(Symbol.of("define!"));
        return output.iterator();
    }
}
