package net.jloop.rejoice.types;

import net.jloop.rejoice.Atom;

public class MultilineComment implements Atom, Comment {

    private final String text;

    public MultilineComment(String text) {
        this.text = text;
    }

    @Override
    public String print() {
        // TODO(jeremy) Support this when there are language-specific printers
        return "";
    }
}
