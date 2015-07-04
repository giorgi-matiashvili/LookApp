package com.lookapp.api.request;

/**
 * Created by user on 04/07/2015.
 */
public class FavouriteAddRequest {

    private String sessionId;
    private long spotId;

    public FavouriteAddRequest(String sessionId,long spotId){
        this.sessionId = sessionId;
        this.spotId = spotId;
    }

    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public long getSpotId() {
        return spotId;
    }
    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }

}
