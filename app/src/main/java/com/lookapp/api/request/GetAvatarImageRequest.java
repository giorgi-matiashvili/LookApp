package com.lookapp.api.request;


@PropertyOrder({"spotId"})
public class GetAvatarImageRequest{


    private long spotId;

    public long getSpotId() {
        return spotId;
    }

    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }
}