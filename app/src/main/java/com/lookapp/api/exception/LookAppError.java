package com.lookapp.api.exception;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class LookAppError {
    private int statusCode;
    private String message;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message + " " + statusCode; //To change body of generated methods, choose Tools | Templates.

    }
}
