package com.lookapp.api.request;

public class CoverImageRequest {
	
	private long spotId;
	public CoverImageRequest (long spotId){
		this.spotId = spotId;
	}
	public long getSpotId() {
		return spotId;
	}
	public void setSpotId(long spotId) {
		this.spotId = spotId;
	}
	
	

}
