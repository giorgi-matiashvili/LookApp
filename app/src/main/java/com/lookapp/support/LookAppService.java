package com.lookapp.support;

import com.google.gson.reflect.TypeToken;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.api.request.ReserveRequest;
import com.lookapp.api.request.factory.RequestFactory;
import com.lookapp.bean.LoginResponse;
import com.lookapp.bean.RatingResponse;
import com.lookapp.bean.Sit;
import com.lookapp.bean.SmsCode;
import com.lookapp.bean.Spot;
import com.lookapp.bean.SpotForAdmin;
import com.lookapp.settings.ServerConstants;

import java.util.List;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class LookAppService {

    private LookAppTransport lat;
    private RequestFactory rf;


    private static LookAppService instance;

    private LookAppService() {
        lat = new LookAppTransport();
        rf = new RequestFactory();
    }

    public static LookAppService getInstance() {
        if (instance == null) {
            instance = new LookAppService();
        }
        return instance;
    }

    public List<Spot> getSpotList() throws LookAppException{
        return lat.execute(ServerConstants.GET_SPOT_LIST_SERVLET, null, new TypeToken<List<Spot>>() {}.getType());
    }

    public byte[] getAvatar(long spotId) throws LookAppException{
        return lat.getBinaryData(ServerConstants.GET_AVATAR_SERVLET, rf.newGetAvatarImageRequest(spotId));
    }

    public byte[] getCoverImage(long spotId) throws LookAppException{
        return lat.getBinaryData(ServerConstants.GET_COVER_IMAGE_SERVLET, rf.newGetCoverImageRequest(spotId));
    }

    public SmsCode getSmsCode(String number) throws LookAppException{
        return lat.execute(ServerConstants.SMS_CODE_SERVLET, rf.newGetSmsCodeRequest(number), SmsCode.class);
    }

    public void register(String fullName, String userName, String password, String number) throws LookAppException{
        lat.execute(ServerConstants.REGISTER_SERVLET,rf.newRegisterRequest(fullName, userName,password,number),Void.class);
    }

    public LoginResponse login(String userName, String password) throws LookAppException{
        return lat.execute(ServerConstants.LOGIN_SERVLET,rf.newLoginRequest(userName, password),LoginResponse.class);
    }

    public List<Long> getFavouriteSpotIds(String sessionId) throws LookAppException{
        return lat.execute(ServerConstants.FAVOURITE_IDS_SERVLET, rf.newFavouriteIdsRequest(sessionId), new TypeToken<List<Long>>(){}.getType());
    }

    public void deleteFavourite(String sessionId, long spotId) throws LookAppException{
        lat.execute(ServerConstants.FAVOURITE_DELETE_SERVLET,rf.newFavouriteDeleteRequest(sessionId,spotId),Void.class);
    }
    public void addFavourite(String sessionId, long spotId) throws LookAppException{
        lat.execute(ServerConstants.FAVOURITE_ADD_SERVLET,rf.newFavouriteAddRequest(sessionId, spotId),Void.class);
    }

    public RatingResponse addRating(String sessionId, long spotId, double rating) throws LookAppException{
        return lat.execute(ServerConstants.RATING_SERVLET, rf.newRatingRequest(sessionId, spotId,rating),RatingResponse.class);
    }

    public SpotForAdmin getSpotForAdmin(long adminSpotId) throws LookAppException{
        return lat.execute(ServerConstants.SPOT_FOR_ADMIN_SERVLET,rf.newSpotForAdminRequest(adminSpotId), SpotForAdmin.class);
    }

    public void updateSpotInfo(SpotForAdmin spotForAdmin)throws LookAppException{
        lat.execute(ServerConstants.UPDATE_SPOT_SERVLET,spotForAdmin,Void.class);
    }

    public void updateEventInfo(SpotForAdmin spotForAdmin) throws LookAppException{
        lat.execute(ServerConstants.UPDATE_EVENT_SERVLET,spotForAdmin,Void.class);
    }

    public void updateFreeSitsInfo(long spotId,String sits) throws LookAppException{
        lat.execute(ServerConstants.UPDATE_SITS_SERVLET,rf.newUpdateSitsRequest(spotId, sits),Void.class);
    }

    public List<ReserveRequest> getBookingInfos(long spotId) throws LookAppException{
        return lat.execute(ServerConstants.BOOKING_INFOS_SERVLET, rf.newBookingInfosRequest(spotId), new TypeToken< List<ReserveRequest>>(){}.getType());
    }

    public void reserve(String sessionId,String time, String sits, String text,long spotId) throws LookAppException{
        lat.execute(ServerConstants.RESERVE_PLACE_SERVLET, rf.newReserveRequest(sessionId, time, sits, text, spotId),Void.class);
    }

    public void answerBooking(long spotId, String spotName, String text,String number) throws LookAppException{
        lat.execute(ServerConstants.ANSWER_BOOKING_SERVLET,rf.newAnswerBookingRequest(spotId, spotName, text, number),Void.class);
    }

    public Sit getSits(long spotId) throws LookAppException{
        return lat.execute(ServerConstants.GET_SITS_SERVLET,rf.newGetSitsRequest(spotId), Sit.class);
    }

    public void addMenuItem(long spotId, String menuName, String menuNameKa, double price, String description,String descriptionKa) throws LookAppException{
        lat.execute(ServerConstants.ADD_MANU_ITEM_SERVLET,rf.newAddMenuItemRequest(spotId,menuName,menuNameKa,price,description,descriptionKa), Void.class);
    }

}
