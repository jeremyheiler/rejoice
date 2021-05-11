package net.jloop.rejoice;

import net.jloop.rejoice.types.Quote;
import net.jloop.rejoice.types.Symbol;

public interface Quotable extends Atom {

    Quote quote();

    Symbol symbol();
}
