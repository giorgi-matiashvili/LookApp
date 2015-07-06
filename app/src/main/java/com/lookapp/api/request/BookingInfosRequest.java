package com.lookapp.api.request;

/**
 * Created by user on 06/07/2015.
 */
public class BookingInfosRequest {
    private long spotId;

    public BookingInfosRequest(long spotId) {
        this.spotId = spotId;
    }

    public long getSpotId() {
        return spotId;
    }

    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }
}
