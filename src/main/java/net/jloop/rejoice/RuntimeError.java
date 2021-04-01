package net.jloop.rejoice;

public class RuntimeError extends RuntimeException {

    private final String stage;

    public RuntimeError(String stage, String message, Exception cause) {
        super(message, cause);
        this.stage = stage;
    }

    public RuntimeError(String stage, Exception cause) {
        this(stage, "", cause);
    }

    public RuntimeError(String stage, Exception cause, String message) {
        this(stage, message, cause);
    }

    public RuntimeError(String stage, String message) {
        this(stage, message, null);
    }

    public String getStage() {
        return stage;
    }
}
