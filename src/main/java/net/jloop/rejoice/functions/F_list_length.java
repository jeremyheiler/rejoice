package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.Int64;
import net.jloop.rejoice.types.List;

public class F_list_length implements Function {

    @Override
    public Stack invoke(Stack stack, Context context) {
        List list = stack.peek(List.class);
        return stack.push(new Int64(list.length()));
    }
}