package net.jloop.rejoice;

public class ParserResult {

    private final Atom atom;
    private final Error error;
    private final boolean eof;

    private ParserResult(Atom atom, Error error, boolean eof) {
        this.atom = atom;
        this.error = error;
        this.eof = eof;
    }

    public static ParserResult eof() {
        return new ParserResult(null, null, true);
    }

    public static ParserResult of(Atom atom) {
        return new ParserResult(atom, null, false);
    }

    public static ParserResult of(Error error) {
        return new ParserResult(null, error, false);
    }

    public boolean isOk() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }

    public boolean isEof() {
        return eof;
    }

    public Atom getAtom() {
        return atom;
    }

    public Error getError() {
        return error;
    }
}
