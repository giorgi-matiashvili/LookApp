package com.lookapp.api.request;

/**
 * Created by user on 05/07/2015.
 */
public class UpdateSitsRequest {
    private String sits;
    private long spotId;

    public UpdateSitsRequest(long spotId, String sits) {
        this.spotId = spotId;
        this.sits = sits;
    }

    public String getSits() {
        return sits;
    }

    public void setSits(String sits) {
        this.sits = sits;
    }

    public long getSpotId() {
        return spotId;
    }

    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }
}
