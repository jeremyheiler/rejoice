package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;
import net.jloop.rejoice.RuntimeError;

public class Char implements Atom {

    private final char value;

    public Char(char value) {
        this.value = value;
    }

    public static Char of(String str) {
        if (str.equals("\\space")) {
            return new Char(' ');
        }
        if (str.equals("\\backspace")) {
            return new Char('\b');
        }
        if (str.equals("\\formfeed")) {
            return new Char('\f');
        }
        if (str.equals("\\newline")) {
            return new Char('\n');
        }
        if (str.equals("\\return")) {
            return new Char('\r');
        }
        if (str.equals("\\tab")) {
            return new Char('\t');
        }
        if (str.startsWith("\\u")) {
            throw new RuntimeError("PARSE", "Unicode characters literals are not yet supported");
        }
        if (str.substring(1).length() == 1) {
            return new Char(str.charAt(1));
        }
        throw new RuntimeError("PARSE", "Character literals must represent a single character '" + str + "'");
    }

    public char get() {
        return value;
    }

    @Override
    public String print() {
        if (value == ' ') {
            return "\\space";
        }
        if (value == '\b') {
            return "\\backspace";
        }
        if (value == '\f') {
            return "\\formfeed";
        }
        if (value == '\n') {
            return "\\newline";
        }
        if (value == '\r') {
            return "\\return";
        }
        if (value == '\t') {
            return "\\tab";
        }
        if (Character.isISOControl(value)) {
            return "\\u" + Integer.toHexString(value);
        }
        return "\\" + value;
    }

    @Override
    public String write() {
        return String.valueOf(value);
    }
}
