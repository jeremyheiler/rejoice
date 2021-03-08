package net.jloop.rejoice;

public class Str implements Literal {

    private final String value;

    public Str(String value) {
        this.value = value;
    }

    public Str concat(Str that) {
        return new Str(this.value + that.value);
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
