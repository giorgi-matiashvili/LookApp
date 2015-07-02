package com.lookapp.api.request;

/**
 * Created by user on 02/07/2015.
 */
public class FavouriteIdsRequest {

    private String sessionId;
    public FavouriteIdsRequest(String sessionId){
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
