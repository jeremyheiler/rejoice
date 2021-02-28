package net.jloop.rejoice;

import net.jloop.rejoice.literals.Bool;
import net.jloop.rejoice.literals.Int64;
import net.jloop.rejoice.literals.Quote;
import net.jloop.rejoice.literals.Str;
import net.jloop.rejoice.literals.Symbol;

import java.util.ArrayList;

public class Parser {

    public Program parse(Buffer input) {
        ArrayList<Atom> atoms = new ArrayList<>();
        while (input.hasMore()) {
            Atom atom = parseAtom(input);
            if (atom != null) {
                atoms.add(atom);
            }
        }
        return new Program(atoms);
    }

    public Atom parseAtom(Buffer input) {
        if (input.hasMore()) {
            char c = input.get();
            if (c == '"') {
                return parseString(input);
            } else if (c == '[') {
                return parseStack(input);
            } else if (c == '\'') {
                return new Quote(parseAtom(input));
            } else {
                input.undo();
            }
        }
        StringBuilder buf = new StringBuilder();
        while (input.hasMore()) {
            char c = input.get();
            if (Character.isWhitespace(c)) {
                break;
            } else if (c == ']') {
                input.undo();
                break;
            } else {
                buf.append(c);
            }
        }
        while (input.hasMore()) {
            char c = input.get();
            if (!Character.isWhitespace(c)) {
                input.undo();
                break;
            }
        }
        if (!buf.isEmpty()) {
            String value = buf.toString();
            if (value.matches("-?\\d+")) {
                return new Int64(Long.parseLong(value));
            } else if (value.equals("true")) {
                return new Bool(true);
            } else if (value.equals("false")) {
                return new Bool(false);
            } else {
                return new Symbol(value);
            }
        }
        return null;
    }

    public Str parseString(Buffer input) {
        int mark = input.at();
        while (input.hasMore()) {
            char c = input.get();
            if (c == '"') {
                return new Str(input.sub(mark, input.at() - 1));
            }
        }
        throw new RuntimeException("End of input while parsing a string");
    }

    public Stack parseStack(Buffer input) {
        ArrayList<Atom> atoms = new ArrayList<>();
        while (input.hasMore()) {
            char c = input.get();
            if (c == ']') {
                return new Stack(atoms);
            } else {
                input.undo();
                Atom atom = parseAtom(input);
                atoms.add(atom);
            }
        }
        throw new RuntimeException("End of input while parsing a vector");
    }

    public static class Buffer {

        private final String data;
        private int index = 0;

        public Buffer(String data) {
            this.data = data;
        }

        public boolean hasMore() {
            return index < data.length();
        }

        public int at() {
            return index;
        }

        public char look() {
            return data.charAt(index);
        }

        public void undo() {
            index--;
        }

        public char get() {
            return data.charAt(index++);
        }

        public String sub(int start, int end) {
            if (start >= end) {
                throw new RuntimeException("The start index must be less than the end index.");
            }
            return data.substring(start, end);
        }
    }
}
