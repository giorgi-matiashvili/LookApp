package com.lookapp.support;

import com.google.gson.reflect.TypeToken;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.api.request.factory.RequestFactory;
import com.lookapp.bean.SmsCode;
import com.lookapp.bean.Spot;
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

    public SmsCode getSmsCode(String number) throws LookAppException{
        return lat.execute(ServerConstants.SMS_CODE_SERVLET, rf.newGetSmsCodeRequest(number), SmsCode.class);
    }

    public void register(String userName, String password, String number) throws LookAppException{
        lat.execute(ServerConstants.REGISTER_SERVLET,rf.newRegisterRequest(userName,password,number),Void.class);
    }
}
