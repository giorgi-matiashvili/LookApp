package com.lookapp.support;

import com.google.gson.reflect.TypeToken;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.api.request.factory.RequestFactory;
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

}
