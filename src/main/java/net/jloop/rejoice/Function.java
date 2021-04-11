package net.jloop.rejoice;

import net.jloop.rejoice.types.List;

@FunctionalInterface
public interface Function {

    Stack invoke(Stack stack, Context context);

    final class Interpreted implements Function {

        private final List body;

        public Interpreted(List body) {
            this.body = body;
        }

        @Override
        public Stack invoke(Stack stack, Context context) {
            return context.interpreter().interpret(stack, context, body);
        }

        public List body() {
            return body;
        }
    }
}
