package net.jloop.rejoice;

public class ParserResult {

    private final Atom atom;
    private final RejoiceError error;

    private ParserResult(Atom atom, RejoiceError error) {
        this.atom = atom;
        this.error = error;
    }

    public static ParserResult of(Atom atom) {
        return new ParserResult(atom, null);
    }

    public static ParserResult of(RejoiceError error) {
        return new ParserResult(null, error);
    }

    public boolean isOk() {
        return error == null;
    }

    public boolean isEof() {
        return isError() && error.getMessage().equals("EOF");
    }

    public boolean isError() {
        return error != null;
    }

    public Atom getAtom() {
        return atom;
    }

    public RejoiceError getError() {
        return error;
    }
}
