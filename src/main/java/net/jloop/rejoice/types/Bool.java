package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public enum Bool implements Atom {

    True, False;

    public static Bool of(boolean value) {
        return value ? True : False;
    }

    public static Bool of(String value) {
        if (value.equals("true")) {
            return True;
        } else if (value.equals("false")) {
            return False;
        } else {
            throw new IllegalArgumentException("Boolean values must be either 'true' or 'false', but received: '" + value + "'");
        }
    }

    @Override
    public String print() {
        return this == True ? "true" : "false";
    }
}
