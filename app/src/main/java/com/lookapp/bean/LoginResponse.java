package com.lookapp.bean;

/**
 * Created by user on 02/07/2015.
 */
public class LoginResponse {

    private String sessionId;
    private long adminSpotId;

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

    public long getAdminSpotId() {
        return adminSpotId;
    }

    public void setAdminSpotId(long adminSpotId) {
        this.adminSpotId = adminSpotId;
    }
}
