package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public final class Str implements Atom {

    private final String value;

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
    public Type type() {
        return Type.String;
    }

    @Override
    public String print() {
        return "\"" + value + "\"";
    }

    @Override
    public String write() {
        return value;
    }
}
