package com.lookapp.api.request;

/**
 * Created by user on 02/07/2015.
 */
public class RegisterRequest {


    private String fullName;
    private String userName;
    private String password;
    private String number;

    public RegisterRequest(String fullName, String userName, String password, String number){
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
