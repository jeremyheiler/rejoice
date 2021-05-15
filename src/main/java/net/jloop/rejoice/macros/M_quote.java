package net.jloop.rejoice.macros;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.MacroHelper;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;
import net.jloop.rejoice.types.Symbol;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

public final class M_quote implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Quote quote = new Quote();

        List<Value> list = MacroHelper.collect(env, input, Symbol.of("]"));
        return Collections.<Value>singletonList(new Quote(list)).iterator();
    }
}
