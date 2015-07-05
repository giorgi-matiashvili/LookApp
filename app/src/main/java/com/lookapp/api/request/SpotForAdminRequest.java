package com.lookapp.api.request;

/**
 * Created by user on 05/07/2015.
 */
public class SpotForAdminRequest {
    private long adminSpotId;

    public SpotForAdminRequest(long adminSpotId) {
        this.adminSpotId = adminSpotId;
    }

    public long getAdminSpotId() {
        return adminSpotId;
    }

    public void setAdminSpotId(long adminSpotId) {
        this.adminSpotId = adminSpotId;
    }
}
