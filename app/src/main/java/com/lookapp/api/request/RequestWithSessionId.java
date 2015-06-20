package com.lookapp.api.request;

/**
 * Created by Giorgi on 6/20/2015.
 */
@PropertyOrder({"sessionId"})
public class RequestWithSessionId {

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
