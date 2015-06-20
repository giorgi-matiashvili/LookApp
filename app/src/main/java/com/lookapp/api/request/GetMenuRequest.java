package com.lookapp.api.request;

/**
 * Created by Giorgi on 6/20/2015.
 */
@PropertyOrder({"spotId"})
public class GetMenuRequest {

    private long spotId;

    public long getSpotId() {
        return spotId;
    }

    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }
}
