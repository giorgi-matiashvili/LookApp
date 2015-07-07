package com.lookapp.api.request;

public class DeleteMenuItemRequest {
	
	private long menuItemId;
	
	public DeleteMenuItemRequest(long menuItemId){
		this.menuItemId = menuItemId;
	}

	public long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(long menuItemId) {
		this.menuItemId = menuItemId;
	}
	
	
	

}
