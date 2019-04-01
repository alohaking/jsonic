package org.kevin.exception;

public class SonicException extends RuntimeException {
    private static final long serialVersionUID = -8274679139300220262L;

    public SonicException() {
    }

    public SonicException(String message, Throwable cause) {
        super(message, cause);
    }

    public SonicException(String message) {
        super(message);
    }

    public SonicException(Throwable cause) {
        super(cause);
    }

}