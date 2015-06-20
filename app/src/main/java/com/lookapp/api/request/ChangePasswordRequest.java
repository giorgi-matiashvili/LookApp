package com.lookapp.api.request;

/**
 * Created by Giorgi on 6/20/2015.
 */
@PropertyOrder({"sessionId", "oldPassword", "newPassword"})
public class ChangePasswordRequest {

    private String sessionId;
    private String oldPassword;
    private String newPassword;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
