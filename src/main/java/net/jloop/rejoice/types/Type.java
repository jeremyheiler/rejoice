package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

import java.util.Objects;

public final class Type implements Atom {

    public static final Type Bool = new Type("bool");
    public static final Type Char = new Type("char");
    public static final Type I64 = new Type("i64");
    public static final Type Keyword = new Type("keyword");
    public static final Type List = new Type("list");
    public static final Type Quote = new Type("quote");
    public static final Type Stack = new Type("stack");
    public static final Type String = new Type("string");
    public static final Type Symbol = new Type("symbol");
    public static final Type Type = new Type("type");

    private final String name;

    private Type(String name) {
        this.name = name;
    }

    public static Type of(String name) {
        return new Type(name);
    }

    public String name() {
        return name;
    }

    @Override
    public Type type() {
        return Type.Type;
    }

    @Override
    public String print() {
        return "^" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
