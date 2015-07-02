package com.lookapp.api.request;

/**
 * Created by user on 02/07/2015.
 */
public class SmsCodeRequest {

    private String userNumber;

    public SmsCodeRequest(String userNumber){
        this.userNumber = userNumber;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

}
