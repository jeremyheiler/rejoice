package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.RuntimeError;
import net.jloop.rejoice.Value;
import net.jloop.rejoice.protocols.Cons;

public class Str implements Atom, Cons {

    private String value;

    public Str(String value) {
        this.value = value;
    }

    @Override
    public void cons(Value value) {
        if (!(value instanceof Char)) {
            throw new RuntimeError("RT", "Cannot cons a non-char to a string");
        }
        this.value = ((Char) value).get() + this.value;
    }

    public Str concat(Str that) {
        return new Str(this.value + that.value);
    }

    public String get() {
        return value.translateEscapes();
    }

    @Override
    public String print() {
        return value;
    }

    @Override
    public String value() {
        return "\"" + value + "\"";
    }
}
