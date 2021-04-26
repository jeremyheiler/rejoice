package net.jloop.rejoice.util;

public enum Exceptions {
    ;

    @SuppressWarnings("unchecked")
    static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }
}
