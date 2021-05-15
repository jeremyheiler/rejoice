package net.jloop.rejoice.functions;

import net.jloop.rejoice.Env;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

public class F_stack_length implements Function {

    @Override
    public Stack call(Env env, Stack data, Deque<Quote> call) {
        Stack st = data.peek(Stack.class);
        return data.push(new Int64(st.length()));
    }
}
