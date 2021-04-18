package net.jloop.rejoice;

@FunctionalInterface
public interface Function {

    Stack invoke(Stack stack, Context context);
}
