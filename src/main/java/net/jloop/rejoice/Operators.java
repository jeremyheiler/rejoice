package net.jloop.rejoice;

public class Operators {

    /*
     * INTEGER OPERATORS
     */

    // i j -> k
    public static Stack _divide(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.divide(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _minus(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.minus(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _mod(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.mod(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _multiply(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.multiply(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack _plus(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.plus(j);
        return stack.push(k);
    }

    // n -> m
    public static Stack abs(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.abs();
        return stack.push(m);
    }

    // i j -> k
    public static Stack max(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.max(j);
        return stack.push(k);
    }

    // i j -> k
    public static Stack min(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 j = stack.consume(Int64.class);
        Int64 i = stack.consume(Int64.class);
        Int64 k = i.min(j);
        return stack.push(k);
    }

    // n -> m
    public static Stack pred(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.minus(new Int64(1));
        return stack.push(m);
    }

    // n -> m
    public static Stack sign(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.signum();
        return stack.push(m);
    }

    // n -> m
    public static Stack succ(@SuppressWarnings("unused") Library library, Stack stack) {
        Int64 n = stack.consume(Int64.class);
        Int64 m = n.plus(new Int64(1));
        return stack.push(m);
    }

    /*
     * BOOLEAN OPERATORS
     */

    // b t f -> r
    // If b is true, leave t on the stack. If b is false, leave f on the stack.
    public static Stack choice(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom f = stack.consume(Atom.class);
        Atom t = stack.consume(Atom.class);
        Bool b = stack.consume(Bool.class);
        Atom r = b.isTrue() ? t : f;
        return stack.push(r);
    }

    // x y -> b
    public static Stack _equal(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Bool b = new Bool(x.equals(y));
        return stack.push(b);
    }

    /*
     * STACK OPERATORS
     */

    // x -> x x
    public static Stack dup(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom x = stack.peek(Atom.class);
        return stack.push(x);
    }

    // x y -> x x y
    public static Stack dupd(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.peek(Atom.class);
        return stack.push(x).push(y);
    }

    // x [... [x xs] ...] -> [xs]
    // Given a list of lists, return the rest of the first list where the type of the first element of that list matches the type of x.
    // If none match, return the last list. If there's only one sub list, return the whole sub list.
    public static Stack opcase(@SuppressWarnings("unused") Library library, Stack stack) {
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
    public static Stack pop(@SuppressWarnings("unused") Library library, Stack stack) {
        return stack.pop();
    }

    // x y -> y
    public static Stack popd(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom x = stack.peek(Atom.class);
        return stack.pop().pop().push(x);
    }

    // x y ->
    public static Stack popop(@SuppressWarnings("unused") Library library, Stack stack) {
        return stack.pop().pop();
    }

    // x y z -> z x y
    public static Stack rollup(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(z).push(x).push(y);
    }

    // x y z -> y z x
    public static Stack rolldown(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(z).push(x);
    }

    // x y -> y x
    public static Stack swap(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(x);
    }

    // x y z -> y x z
    public static Stack swapd(@SuppressWarnings("unused") Library library, Stack stack) {
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return stack.push(y).push(x).push(z);
    }

    /*
     * COMBINATOR OPERATORS
     */

    // x [p] -> r
    // Replace x with the result of evaluating the quote p with a stack containing only x.
    public static Stack app1(@SuppressWarnings("unused") Library library, Stack stack) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        Atom r = p.unquote(library, new Stack().push(x)).consume(Atom.class);
        return stack.push(r);
    }

    // x y [p] -> rx ry
    // Replace x and y with the results of evaluating the quote p with a stack containing only x or y, respectively.
    public static Stack app2(Library library, Stack stack) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = p.unquote(library, new Stack().push(y)).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = p.unquote(library, new Stack().push(x)).consume(Atom.class);
        return stack.push(rx).push(ry);
    }

    // x y z [p] -> rx ry rz
    // Replace x, y, and z with the results of evaluating the quote p with a stack containing only x, y, or z, respectively.
    public static Stack app3(Library library, Stack stack) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom rz = p.unquote(library, new Stack().push(z)).consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom ry = p.unquote(library, new Stack().push(y)).consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        Atom rx = p.unquote(library, new Stack().push(x)).consume(Atom.class);
        return stack.push(rx).push(ry).push(rz);
    }

    // [p] [q] -> ~rp ~rq
    public static Stack b(Library library, Stack stack) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        stack = p.unquote(library, stack);
        stack = q.unquote(library, stack);
        return stack;
    }

    // x [p] [q] -> ~rp ~rq
    public static Stack cleave(Library library, Stack stack) {
        List q = stack.consume(List.class);
        List p = stack.consume(List.class);
        Stack copyP = stack.copy();
        Stack copyQ = stack.copy();
        Atom rp = p.unquote(library, copyP).consume(Atom.class);
        Atom rq = q.unquote(library, copyQ).consume(Atom.class);
        return stack.pop().push(rp).push(rq);
    }

    // [p] -> ...
    public static Stack i(Library library, Stack stack) {
        List p = stack.consume(List.class);
        return p.unquote(library, stack);
    }

    // [b] [t] [f] -> ...
    public static Stack ifte(Library library, Stack stack) {
        List f = stack.consume(List.class);
        List t = stack.consume(List.class);
        List b = stack.consume(List.class);
        Stack copy = stack.copy();
        if (b.unquote(library, copy).consume(Bool.class).isTrue()) {
            return t.unquote(library, stack);
        } else {
            return f.unquote(library, stack);
        }
    }

    // x [p] -> ... x
    public static Stack dip(Library library, Stack stack) {
        List p = stack.consume(List.class);
        Atom x = stack.consume(Atom.class);
        return p.unquote(library, stack).push(x);
    }

    // x y [p] -> ... x y
    public static Stack dipd(Library library, Stack stack) {
        List p = stack.consume(List.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return p.unquote(library, stack).push(x).push(y);
    }

    // x y z [p] -> ... x y z
    public static Stack dipdd(Library library, Stack stack) {
        List p = stack.consume(List.class);
        Atom z = stack.consume(Atom.class);
        Atom y = stack.consume(Atom.class);
        Atom x = stack.consume(Atom.class);
        return p.unquote(library, stack).push(x).push(y).push(z);
    }

    // a [p] -> b
    public static Stack map(Library library, Stack stack) {
        List p = stack.consume(List.class);
        List a = stack.consume(List.class);
        List b = new List();
        for (Atom atom : a) {
            b.append(p.unquote(library, new Stack().push(atom)).consume(Atom.class));
        }
        return stack.push(b);
    }

    // [p] -> r
    public static Stack nullary(Library library, Stack stack) {
        List p = stack.consume(List.class);
        Atom r = p.unquote(library, stack.copy()).consume(Atom.class);
        return stack.push(r);
    }

    // [b] [d] -> ...
    public static Stack _while(Library library, Stack stack) {
        List d = stack.consume(List.class);
        List b = stack.consume(List.class);
        while (b.unquote(library, stack).consume(Bool.class).isTrue()) {
            stack = d.unquote(library, stack);
        }
        return stack;
    }

    // [p] -> [p] ...
    public static Stack x(Library library, Stack stack) {
        List p = stack.peek(List.class);
        return p.unquote(library, stack);
    }

    //
    public static Stack y(@SuppressWarnings("unused") Library library, @SuppressWarnings("unused") Stack stack) {
        throw new UnsupportedOperationException("y");
    }
}
