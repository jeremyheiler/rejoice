package net.jloop.rejoice;

public class RejoiceError {

    private final String stage;
    private final String message;
    private final String details;
    private final Exception ex;

    public RejoiceError(String stage, String message, String details, Exception ex) {
        this.stage = stage;
        this.message = message;
        this.details = details;
        this.ex = ex;
    }

    public RejoiceError(String stage, String message, String details) {
        this(stage, message, details, null);
    }

    public RejoiceError(String stage, String message) {
        this(stage, message, null, null);
    }

    public RejoiceError(String stage, Exception ex) {
        this(stage, ex.getMessage(), null, ex);
    }

    public String getStage() {
        return stage;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Exception getEx() {
        return ex;
    }
}
