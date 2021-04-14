package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public class Str implements Atom {

    private String value;

    public Str(String value) {
        this.value = value;
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
