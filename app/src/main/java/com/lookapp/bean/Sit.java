package com.lookapp.bean;

public class Sit {
	
	private String sits;
	private long lastUpdate;
	
	public Sit(String sits,long lastUpdate){
		this.sits = sits;
		this.lastUpdate = lastUpdate;
	}

	public String getSits() {
		return sits;
	}

	public void setSits(String sits) {
		this.sits = sits;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	

}
