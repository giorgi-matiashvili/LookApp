package com.lookapp.api.exception;

public class LookAppException extends Exception {
    private static final long serialVersionUID = 1L;

    private LookAppError lookAppError;

    public LookAppException() {
    }

    public LookAppException(String message) {
        super(message);
    }

    public LookAppException(Throwable cause) {
        super(cause);
    }

    public LookAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public LookAppException(int errorCode, String message) {
        lookAppError = new LookAppError();
        lookAppError.setStatusCode(errorCode);
        lookAppError.setMessage(message);
    }

    public LookAppException(LookAppError error) {
        this.lookAppError = error;
    }

    public LookAppError getLookAppError() {
        return lookAppError;
    }

}
