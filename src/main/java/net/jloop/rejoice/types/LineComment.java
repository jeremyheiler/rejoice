package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public class LineComment implements Atom, Comment {

    private final String text;

    public LineComment(String text) {
        this.text = text;
    }

    @Override
    public String print() {
        // TODO(jeremy) Support this when there are language-specific printers
        return "";
    }
}
