package net.jloop.rejoice;

public final class Definition {

    private final String name;
    private final Function function;
    private final boolean pub;

    public Definition(String name, Function function, boolean pub) {
        this.name = name;
        this.function = function;
        this.pub = pub;
    }

    public String name() {
        return name;
    }

    public Function function() {
        return function;
    }

    public boolean isPublic() {
        return pub;
    }
}
