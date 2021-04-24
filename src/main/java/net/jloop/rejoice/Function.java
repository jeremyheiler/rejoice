package net.jloop.rejoice;

@FunctionalInterface
public interface Function {

    Stack invoke(Context context, Stack stack);
}
