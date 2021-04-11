package net.jloop.rejoice.protocols;

import net.jloop.rejoice.Value;

public interface Cons extends Value {

    void cons(Value value);
}
