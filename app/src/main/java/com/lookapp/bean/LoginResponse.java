package com.lookapp.bean;

/**
 * Created by user on 02/07/2015.
 */
public class LoginResponse {

    private String sessionId;

    public LoginResponse() {
        // TODO Auto-generated constructor stub
    }

    public LoginResponse(String sessionId) {
        this.sessionId = sessionId;
    }



    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


}
