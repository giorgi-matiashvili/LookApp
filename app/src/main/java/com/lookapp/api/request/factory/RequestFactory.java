package com.lookapp.api.request.factory;

import com.lookapp.api.request.AddMenuItemRequest;
import com.lookapp.api.request.AnswerBookingRequest;
import com.lookapp.api.request.BookingInfosRequest;
import com.lookapp.api.request.ChangePasswordRequest;
import com.lookapp.api.request.DeleteMenuItemRequest;
import com.lookapp.api.request.FavouriteAddRequest;
import com.lookapp.api.request.FavouriteDeleteRequest;
import com.lookapp.api.request.FavouriteIdsRequest;
import com.lookapp.api.request.GetAvatarImageRequest;
import com.lookapp.api.request.GetCoverImageRequest;
import com.lookapp.api.request.GetMenuRequest;
import com.lookapp.api.request.GetSitsRequest;
import com.lookapp.api.request.LoginRequest;
import com.lookapp.api.request.RatingRequest;
import com.lookapp.api.request.RegisterRequest;
import com.lookapp.api.request.RequestWithSessionId;
import com.lookapp.api.request.ReserveRequest;
import com.lookapp.api.request.SmsCodeRequest;
import com.lookapp.api.request.SpotForAdminRequest;
import com.lookapp.api.request.UpdateSitsRequest;

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


    public SmsCodeRequest newGetSmsCodeRequest(String number) {
        return new SmsCodeRequest(number);
    }

    public RegisterRequest newRegisterRequest(String fullName, String userName, String password, String number) {
        return new RegisterRequest(fullName, userName,password,number);
    }

    public FavouriteIdsRequest newFavouriteIdsRequest(String sessionId) {
        return new FavouriteIdsRequest(sessionId);
    }

    public FavouriteDeleteRequest newFavouriteDeleteRequest(String sessionId, long spotId) {

        return new FavouriteDeleteRequest(sessionId,spotId);
    }

    public FavouriteAddRequest newFavouriteAddRequest(String sessionId, long spotId) {
        return new FavouriteAddRequest(sessionId,spotId);
    }

    public RatingRequest newRatingRequest(String sessionId, long spotId, double rating) {
        return new RatingRequest(sessionId,spotId,rating);
    }

    public SpotForAdminRequest newSpotForAdminRequest(long adminSpotId) {
        return new SpotForAdminRequest(adminSpotId);
    }

    public UpdateSitsRequest newUpdateSitsRequest(long spotId, String sits) {
        return new UpdateSitsRequest(spotId,sits);
    }

    public ReserveRequest newReserveRequest(String sessionId, String time, String sits, String text,long spotId) {
        return new ReserveRequest(sessionId,time,sits,text,spotId);
    }

    public BookingInfosRequest newBookingInfosRequest(long spotId) {
        return new BookingInfosRequest(spotId);
    }

    public AnswerBookingRequest newAnswerBookingRequest(long spotId, String spotName, String text, String number) {
        return new AnswerBookingRequest(spotId,spotName,text,number);
    }

    public GetSitsRequest newGetSitsRequest(long spotId) {
        return new GetSitsRequest(spotId);
    }

    public AddMenuItemRequest newAddMenuItemRequest(long spotId, String menuName, String menuNameKa, double price, String description,String descriptionKa) {
        return new AddMenuItemRequest( spotId, menuName, menuNameKa, price, description,descriptionKa);
    }

    public DeleteMenuItemRequest newDeleteMenuItemRequest(long menuItemId) {
        return new DeleteMenuItemRequest(menuItemId);
    }
}
