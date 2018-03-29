package com.xqlh.heartsmart.api;


import com.xqlh.heartsmart.api.service.ApiService;
import com.xqlh.heartsmart.utils.Constants;

public class RetrofitHelper {
    private static ApiService mIdeaApiService;

    public static ApiService getApiService(){
        return mIdeaApiService;
    }
    static {
       mIdeaApiService= IdeaApi.getApiService(ApiService.class, Constants.BASE_URL);
    }
}
