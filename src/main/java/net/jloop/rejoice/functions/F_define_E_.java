package net.jloop.rejoice.functions;

import net.jloop.rejoice.Context;
import net.jloop.rejoice.Function;
import net.jloop.rejoice.Stack;
import net.jloop.rejoice.types.List;
import net.jloop.rejoice.types.Symbol;

public final class F_define_E_ implements Function {

    // TODO: Have three options:
    // - public: available to all, automatically exported
    // - protected: available internally and when included, but is  not exported. not available when required
    // - private: available internally, and not when included or required
    private final boolean pub;

    public F_define_E_(boolean pub) {
        this.pub = pub;
    }

    @Override
    public Stack invoke(Stack stack, Context context) {
        Symbol name = stack.consume(Symbol.class);
        List body = stack.consume(List.class);
        Function function = new Function.Interpreted(body);
        context.current().define(name.name(), function, pub);
        return stack;
    }
}
