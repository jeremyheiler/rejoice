package net.jloop.rejoice;

import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Stack;

import java.util.Deque;

public interface Invocable {

    Stack invoke(Env env, Stack data, Deque<Quote> call);
}
