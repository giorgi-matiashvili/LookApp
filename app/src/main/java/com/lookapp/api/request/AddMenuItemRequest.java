package com.lookapp.api.request;

/**
 * Created by user on 06/07/2015.
 */
public class AddMenuItemRequest {
    private long spotId;
    private String menuName;
    private String menuNameKa;
    private double price;
    private String description;
    private String descriptionKa;

    public AddMenuItemRequest(long spotId, String menuName, String menuNameKa, double price, String description, String descriptionKa) {
        this.spotId = spotId;
        this.menuName = menuName;
        this.menuNameKa = menuNameKa;
        this.price = price;
        this.description = description;
        this.descriptionKa = descriptionKa;

    }


    public long getSpotId() {
        return spotId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuNameKa() {
        return menuNameKa;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
