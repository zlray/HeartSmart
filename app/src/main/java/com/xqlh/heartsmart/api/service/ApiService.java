package com.xqlh.heartsmart.api.service;

import com.xqlh.heartsmart.bean.EntityArticleBeautiful;
import com.xqlh.heartsmart.bean.EntityArticleDetail;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.bean.EntityBindPhone;
import com.xqlh.heartsmart.bean.EntityCheckAccount;
import com.xqlh.heartsmart.bean.EntityCheckMessage;
import com.xqlh.heartsmart.bean.EntityCheckPhone;
import com.xqlh.heartsmart.bean.EntityGetMessage;
import com.xqlh.heartsmart.bean.EntityGetPhoneByAccount;
import com.xqlh.heartsmart.bean.EntityLogin;
import com.xqlh.heartsmart.bean.EntityProductCategory;
import com.xqlh.heartsmart.bean.EntityProductDetail;
import com.xqlh.heartsmart.bean.EntityUpdatePassword;
import com.xqlh.heartsmart.bean.EntityUserInfor;
import com.xqlh.heartsmart.bean.EntityWelcome;

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

    //获取短信
    @GET("api/account/smscodev2")
    Observable<EntityGetMessage> GetMessage2(@Query("string") String string);


    //修改密码
    @FormUrlEncoded
    @POST("api/account/resetpassword")
    Observable<EntityUpdatePassword> ForgetUpdatePassword(@Field("TelephoneToken") String TelephoneToken,
                                                          @Field("VerCode") String VerCode,
                                                          @Field("password") String password,
                                                          @Field("token") String token);

    //登录后修改密码

    @FormUrlEncoded
    @POST("api/account/cpassword")
    Observable<EntityUpdatePassword> UpdatePassword(@Header("Authorization") String Authorization,
                                                    @Field("Password") String Password,
                                                    @Field("RPassword") String RPassword);


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

    //获得产品信息信息
    @GET("api/product/productinfo")
    Observable<EntityProductDetail> getProductDetail(@Query("id") String id);

    //获得用户的信息
    @GET("api/account/userinfo")
    Observable<EntityUserInfor> getUserInfor(@Header("Authorization") String Authorization);

    //通过查询文章接口 来获取最新的文章,浏览次数最多等
    @FormUrlEncoded
    @POST("api/article/aritclelist")
    Observable<EntityArticleNewest> getArticleQuery(@Field("keywords") String keywords,
                                                     @Field("ArticleTypeID") String ArticleTypeID,
                                                     @Field("LabelIds") String[] LabelIds,
                                                     @Field("pageIndex") int pageIndex,
                                                     @Field("pageSize") int pageSize,
                                                     @Field("orderbytype") int orderbytype);

    //美文
    @FormUrlEncoded
    @POST("api/article/aritclelist")
    Observable<EntityArticleBeautiful> getArticleBeautiful(@Field("keywords") String keywords,
                                                       @Field("ArticleTypeID") String ArticleTypeID,
                                                       @Field("LabelIds") String[] LabelIds,
                                                       @Field("pageIndex") int pageIndex,
                                                       @Field("pageSize") int pageSize,
                                                       @Field("orderbytype") int orderbytype);


    //获取文章的详细内容
    @GET("api/article/aritcle")
    Observable<EntityArticleDetail> getArticleDetail(@Query("id") String id);
}
