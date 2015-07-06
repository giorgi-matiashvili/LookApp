package com.lookapp.api.request;

/**
 * Created by user on 06/07/2015.
 */
public class ReserveRequest {
    private final long spotId;
    private String sessionId;
    private String time;
    private String sits;
    private String text;
    private String number;

    public ReserveRequest(String sessionId, String time, String sits, String text, long spotId) {
        this.sessionId = sessionId;
        this.time = time;
        this.sits = sits;
        this.text = text;
        this.spotId = spotId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSits() {
        return sits;
    }

    public void setSits(String sits) {
        this.sits = sits;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSpotId() {
        return spotId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
