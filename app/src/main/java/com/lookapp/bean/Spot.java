package com.lookapp.bean;


import com.google.gson.annotations.Expose;

public class Spot {


    @Expose private long spotId;
    @Expose private String spotName;
    @Expose private String spotAddress;
    @Expose private String description;
    @Expose private String contactInfo;
    @Expose private String workingHours;
    @Expose private boolean hasWifi;
    @Expose private boolean hasNonSmokerArea;
    @Expose private boolean canReservePlace;
    @Expose private String rating;
    @Expose private String eventDescription;
    @Expose private double longitude;
    @Expose private double latitude;
    @Expose private String type;
    @Expose private String wifiPassword;
    private byte[] avatar;

    public Spot() {

    }

    public Spot(long spotId, String spotName, String spotAddress,
                String description, String contactInfo, String workingHours,
                boolean hasWifi, boolean hasNonSmokerArea, boolean canReservePlace,
                String rating, String eventDescription, double longitude, double latitude, String type, String wifiPassword) {

        this.spotId = spotId;
        this.spotName = spotName;
        this.spotAddress = spotAddress;
        this.description = description;
        this.contactInfo = contactInfo;
        this.workingHours = workingHours;
        this.hasWifi = hasWifi;
        this.hasNonSmokerArea = hasNonSmokerArea;
        this.canReservePlace = canReservePlace;
        this.rating = rating;
        this.eventDescription = eventDescription;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.wifiPassword = wifiPassword;


    }


    public Spot(String spotName, String spotAddress,
                String description, String contactInfo, String workingHours,
                boolean hasWifi, boolean hasNonSmokerArea, boolean canReservePlace,
                String rating, String eventDescription, double longitude, double latitude, String type, String wifiPassword) {

        this.spotName = spotName;
        this.spotAddress = spotAddress;
        this.description = description;
        this.contactInfo = contactInfo;
        this.workingHours = workingHours;
        this.hasWifi = hasWifi;
        this.hasNonSmokerArea = hasNonSmokerArea;
        this.canReservePlace = canReservePlace;
        this.rating = rating;
        this.eventDescription = eventDescription;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.wifiPassword = wifiPassword;


    }

    public long getSpotId() {
        return spotId;
    }

    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean isHasNonSmokerArea() {
        return hasNonSmokerArea;
    }

    public void setHasNonSmokerArea(boolean hasNonSmokerArea) {
        this.hasNonSmokerArea = hasNonSmokerArea;
    }

    public boolean isCanReservePlace() {
        return canReservePlace;
    }

    public void setCanReservePlace(boolean canReservePlace) {
        this.canReservePlace = canReservePlace;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }


    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

}
