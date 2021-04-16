package net.jloop.rejoice;

public interface Value {

    default Stack interpret(Stack stack, Context context) {
        return stack.push(this);
    }

    default Value quote() {
        return this;
    }

    default Value unquote(Module module) {
        return this;
    }

    // Return the printable representation of the value
    String print();

    // Return the parsable representation of the value
    String value();
}
