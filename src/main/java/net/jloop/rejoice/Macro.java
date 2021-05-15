package net.jloop.rejoice;

import java.util.Iterator;

public interface Macro {

    Iterator<Value> call(Env env, Iterator<Value> input);
}
