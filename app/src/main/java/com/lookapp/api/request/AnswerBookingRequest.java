package com.lookapp.api.request;

/**
 * Created by user on 06/07/2015.
 */
public class AnswerBookingRequest {
    private long spotId;
    private String number;
    private String spotName;
    private String text;


    public AnswerBookingRequest(long spotId,String spotName, String text, String number) {
        this.spotName = spotName;
        this.text = text;
        this.number = number;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getSpotId() {
        return spotId;
    }

    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }
}
