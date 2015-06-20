package com.lookapp.api.request;

/**
 * Created by Giorgi on 6/20/2015.
 */
@PropertyOrder({"userName", "password"})
public class LoginRequest {

    private String userName;
    private String password;

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
}
