package com.lookapp.api.request;

public class RatingRequest {
	
	private String sessionId;
	private long spotId;
	private double rating;
	
	public RatingRequest(String sessionId,long spotId,double rating){
		this.sessionId = sessionId;
		this.spotId = spotId;
		this.rating = rating;
		
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
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
	
	

}
