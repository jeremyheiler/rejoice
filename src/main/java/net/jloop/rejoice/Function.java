package net.jloop.rejoice;

import net.jloop.rejoice.types.Stack;

@FunctionalInterface
public interface Function {

    Stack invoke(Context context, Stack stack);
}
