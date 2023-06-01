package com.yaphet.chapa.exception;

/**
 * A ChapaException is thrown to signal a problem during SDK execution.
 */
public class ChapaException extends Exception {

    public ChapaException(String message) {
        super(message);
    }

    public ChapaException(Throwable cause) {
        super(cause);
    }

    public ChapaException(String message, Throwable cause) {
        super(message, cause);
    }
}
