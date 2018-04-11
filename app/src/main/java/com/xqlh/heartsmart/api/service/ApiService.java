package com.xqlh.heartsmart.api.service;

import com.xqlh.heartsmart.ui.bean.EntityBindPhone;
import com.xqlh.heartsmart.ui.bean.EntityCheckAccount;
import com.xqlh.heartsmart.ui.bean.EntityCheckMessage;
import com.xqlh.heartsmart.ui.bean.EntityCheckPhone;
import com.xqlh.heartsmart.ui.bean.EntityGetMessage;
import com.xqlh.heartsmart.ui.bean.EntityGetPhoneByAccount;
import com.xqlh.heartsmart.ui.bean.EntityLogin;
import com.xqlh.heartsmart.ui.bean.EntityUpdatePassword;
import com.xqlh.heartsmart.ui.bean.EntityWelcome;
import com.xqlh.heartsmart.ui.bean.EntityProductCategory;
import com.xqlh.heartsmart.ui.bean.EntityProductDetail;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/3/28.
 */

public interface ApiService {
//    http://open.bnuxq.com//api/Account/token?username=xiaomeinv&password=123456

    //登录
    @GET("api/Account/token")
    Observable<EntityLogin> Login(@Query("username") String username, @Query("password") String password);

    //检测用户名是否存在
    @GET("api/account/existsusername")
    Observable<EntityCheckAccount> CheckAccount(@Query("username") String username);

    //根据用户名获取手机号
    @GET("api/account/checkusername")
    Observable<EntityGetPhoneByAccount> GetPhoneByAccount(@Query("username") String username);

    //检验手机号码是否已注册
    @GET("api/account/existstel")
    Observable<EntityCheckPhone> CheckPhone(@Query("tel") String tel);

    //获取短信
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) WindowsClient/537.36 (KHTML, like Gecko) Bnuxq.com/56.0.2924.87 Safari/537.36")
    @GET("api/account/sendsms")
    Observable<EntityGetMessage> GetMessage(@Query("tel") String tel);

    //修改密码
    @FormUrlEncoded
    @POST("api/account/resetpassword")
    Observable<EntityUpdatePassword> updatePassword(@Field("TelephoneToken") String TelephoneToken,
                                                    @Field("VerCode") String VerCode,
                                                    @Field("password") String password,
                                                    @Field("token") String token);


    //验证短信
    @GET("api/account/checksmscode")
    Observable<EntityCheckMessage> CheckMessage(@Query("token") String token, @Query("telcode") String telcode);

    //绑定手机号
    @FormUrlEncoded
    @POST("api/account/telphone")
    Observable<EntityBindPhone> bindPhone(@Header("Authorization") String Authorization,
                                          @Field("Telphone") String Telphone,
                                          @Field("token") String token,
                                          @Field("telcode") String telcode);

    //app启动页面
    @GET("api/clientapp/apphomepic")
    Observable<EntityWelcome> getWelcome(@Query("appType") int appType);

    @GET("api/product/list")
    Observable<EntityProductCategory> getProductCategory(@Query("ProductStyle") int ProductTypeID,
                                                         @Query("pageIndex") int pageIndex,
                                                         @Query("pageSize") int pageSize);

    @GET("api/product/productinfo")
    Observable<EntityProductDetail> getProductDetail(@Query("id") String id);

}
