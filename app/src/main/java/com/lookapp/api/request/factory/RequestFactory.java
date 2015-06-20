package com.lookapp.api.request.factory;

import com.lookapp.api.request.ChangePasswordRequest;
import com.lookapp.api.request.GetAvatarImageRequest;
import com.lookapp.api.request.GetCoverImageRequest;
import com.lookapp.api.request.GetMenuRequest;
import com.lookapp.api.request.LoginRequest;
import com.lookapp.api.request.RequestWithSessionId;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class RequestFactory {

    public GetAvatarImageRequest newGetAvatarImageRequest(long spotId){
        GetAvatarImageRequest req = new GetAvatarImageRequest();
        req.setSpotId(spotId);
        return req;
    }

    public GetMenuRequest newGetMenuRequest(long spotId){
        GetMenuRequest req = new GetMenuRequest();
        req.setSpotId(spotId);
        return req;
    }

    public GetCoverImageRequest newGetCoverImageRequest(long spotId){
        GetCoverImageRequest req = new GetCoverImageRequest();
        req.setSpotId(spotId);
        return req;
    }

    public LoginRequest newLoginRequest(String userName, String password){
        LoginRequest req = new LoginRequest();
        req.setPassword(password);
        req.setUserName(userName);
        return req;
    }

    public ChangePasswordRequest newChangePasswordRequest(  String sessionId, String oldPassword, String newPassword){
        ChangePasswordRequest req = new ChangePasswordRequest();
        req.setNewPassword(newPassword);
        req.setOldPassword(oldPassword);
        req.setSessionId(sessionId);
        return req;
    }

    public RequestWithSessionId newRequestWithSessionId(String sessionId){
        RequestWithSessionId req = new RequestWithSessionId();
        req.setSessionId(sessionId);
        return req;
    }



}
