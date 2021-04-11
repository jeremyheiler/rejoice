package net.jloop.rejoice;

import net.jloop.rejoice.types.Symbol;

public final class Definition {

    private final Symbol name;
    private final Function function;
    private final boolean pub;

    public Definition(Symbol name, Function function, boolean pub) {
        this.name = name;
        this.function = function;
        this.pub = pub;
    }

    public Symbol name() {
        return name;
    }

    public Function function() {
        return function;
    }

    public boolean isPublic() {
        return pub;
    }
}
