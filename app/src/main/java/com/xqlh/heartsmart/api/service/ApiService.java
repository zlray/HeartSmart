package com.xqlh.heartsmart.api.service;

import com.xqlh.heartsmart.api.bean.EntityLogin;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/3/28.
 */

public interface ApiService {
//    http://open.bnuxq.com//api/Account/token?username=xiaomeinv&password=123456

    @GET("api/Account/token")
    Observable<EntityLogin> Login(@Query("username") String username, @Query("password") String password);


//    @GET("api/data/Android/{p}/{page}")
//    Call<ResponseBody> getAndroidInfo(@Path("page") int page, @Path("p") int p);

}
