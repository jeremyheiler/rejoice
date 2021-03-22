package net.jloop.rejoice;

public class Operators {

    /*
     * INTEGER OPERATORS
     */

    // i j -> k
    public static Stack _divide(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.divide(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _minus(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.minus(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _mod(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.mod(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _multiply(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.multiply(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _plus(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.plus(j);
        return stack.push(k);
    }

    // n -> m
    public static Stack abs(Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.abs();
        return stack.push(m);
    }

    // i j -> k
    public static Stack max(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.max(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack min(Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.min(j);
        return stack.push(k);
    }

    // n -> m
    public static Stack pred(Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.minus(new Int64(1));
        return stack.push(m);
    }

    // no effect
    public static Stack print_BANG_(Stack stack) {
        stack.print();
        return stack;
    }

    // n -> m
    public static Stack sign(Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.signum();
        return stack.push(m);
    }

    // n -> m
    public static Stack succ(Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.plus(new Int64(1));
        return stack.push(m);
    }

    /*
     * BOOLEAN OPERATORS
     */

    // b t f -> r
    // If b is true, leave t on the stack. If b is false, leave f on the stack.
    public static Stack choice(Stack stack) {
        Atom f = stack.consume(Atom.class);
        Atom t = stack.consume(Atom.class);
        Bool b = stack.consume(Bool.class);
        Atom r = b.isTrue() ? t : f;
        return stack.push(r);
    }

    // x y -> b
    public static Stack _equal(Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Bool b = new Bool(x.equals(y));
        return stack.push(b);
    }

    /*
     * STACK OPERATORS
     */

    // x -> x x
    public static Stack dup(Stack stack) {
        Atom x = stack.peek(Atom.class);
        return stack.push(x);
    }

    // x y -> x x y
    public static Stack dupd(Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.peek(Atom.class);
        return stack.push(x).push(y);
    }

    // x [... [x xs] ...] -> [xs]
    // Given a list of lists, return the rest of the first list where the type of the first element of that list matches the type of x.
    // If none match, return the last list. If there's only one sub list, return the whole sub list.
    public static Stack opcase(Stack stack) {
        List list = stack.consume(List.class);
        Atom item = stack.consume(Atom.class);
        if (list.size() == 0) {
            throw new RuntimeException("LIST cannot be empty");
        }
        if (list.size() == 1) {
            return stack.push(list.first());
        }
        int count = 0;
        for (Atom el : list) {
            if (el instanceof List) {
                List l = (List) el;
                if (++count == list.size()) { // last list
                    return stack.push(l);
                }
                if (l.size() == 0) {
                    throw new RuntimeException("LIST cannot be empty");
                }
                if (l.first().getClass() == item.getClass()) {
                    return stack.push(l.rest());
                }
            } else {
                throw new RuntimeException("Expecting a LIST");
            }
        }
        return stack;
    }

    // x ->
    public static Stack pop(Stack stack) {
        return stack.pop();
    }

    // x y -> y
    public static Stack popd(Stack stack) {
        Atom x = stack.peek(Atom.class);
        return stack.pop().pop().push(x);
    }

    // x y ->
    public static Stack popop(Stack stack) {
        return stack.pop().pop();
    }

    // x y z -> z x y
    public static Stack rollup(Stack stack) {
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(z).push(x).push(y);
    }

    // x y z -> y z x
    public static Stack rolldown(Stack stack) {
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(z).push(x);
    }

    // x y -> y x
    public static Stack swap(Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(x);
    }

    // x y z -> y x z
    public static Stack swapd(Stack stack) {
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(x).push(z);
    }
}
