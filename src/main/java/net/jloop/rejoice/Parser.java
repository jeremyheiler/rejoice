package net.jloop.rejoice;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;

public class Parser {

    private static final int EOF = -1;

    public ParserResult parse(PushbackReader input) {
        try {
            int c;

            // Consume whitespace
            while ((c = input.read()) != EOF) {
                if (!(c == ' ' || c == '\t' || c == '\n')) {
                    input.unread(c);
                    break;
                }
            }
            if (c == EOF) {
                return ParserResult.of(new RejoiceError("PARSE", "EOF"));
            }

            // Consume the next aggregate or atom
            if ((c = input.read()) != EOF) {
                if (c == '"') {

                    // Parse string
                    StringBuilder buf = new StringBuilder();
                    while ((c = input.read()) != EOF) {
                        if (c == '"') {
                            return ParserResult.of(new Str(buf.toString()));
                        } else {
                            buf.append((char) c);
                        }
                    }
                    // The loop ended on EOF without closing the string
                    return ParserResult.of(new RejoiceError("PARSE", "Unexpected EOF", "Run-on string"));

                } else if (c == '[') {

                    // Parse list
                    ArrayList<Atom> atoms = new ArrayList<>();
                    while ((c = input.read()) != EOF) {
                        if (c == ']') {
                            return ParserResult.of(new List(atoms));
                        } else {
                            ParserResult result = parse(input);
                            if (result.isError()) {
                                return result;
                            } else {
                                atoms.add(result.getAtom());
                            }
                        }
                    }
                    // The loop ended on EOF without closing the list
                    return ParserResult.of(new RejoiceError("PARSE", "Unexpected EOF", "Run-on list"));

                } else {
                    // TODO(jeremy): Check that c is a valid character for a literal

                    // Parse a literal
                    StringBuilder buf = new StringBuilder().append((char) c);
                    while ((c = input.read()) != EOF) {
                        if (c == ' ' || c == '\t' || c == '\n') {
                            break;
                        } else {
                            buf.append((char) c);
                        }
                    }
                    String value = buf.toString();
                    if (value.matches("-?\\d+")) {
                        return ParserResult.of(new Int64(Long.parseLong(value)));
                    } else if (value.equals("true")) {
                        return ParserResult.of(new Bool(true));
                    } else if (value.equals("false")) {
                        return ParserResult.of(new Bool(false));
                    } else {
                        return ParserResult.of(new Symbol(value));
                    }

                }
            } else {
                return ParserResult.of(new RejoiceError("PARSE", "EOF"));
            }
        } catch (IOException e) {
            return ParserResult.of(new RejoiceError("PARSE", e));
        }
    }
}
