package net.jloop.rejoice;

public interface Literal extends Atom {

    @Override
    default Stack evaluate(Library library, Stack stack) {
        return stack.push(this);
    }
}
