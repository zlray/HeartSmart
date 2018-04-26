package com.xqlh.heartsmart.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xqlh.heartsmart.api.Interceptor.HttpCacheInterceptor;
import com.xqlh.heartsmart.api.Interceptor.HttpHeaderInterceptor;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.LogUtils;
import com.xqlh.heartsmart.utils.ContenxtUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhpan on 2018/3/21.
 */

public class RetrofitService {
    public static OkHttpClient.Builder getOkHttpClientBuilder() {

        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    LogUtils.e("OKHttp-----", URLDecoder.decode(message, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.e("OKHttp-----", message);
                }
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(ContenxtUtils.getContext().getCacheDir(), "cache");

        Log.i("lz", "okHttp缓存文件名字" + cacheFile.getName());

        //设置缓存的大小
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        return new OkHttpClient.Builder()
                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS) //读取超时
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//链接超时
                .addInterceptor(loggingInterceptor) //日志拦截器
                .addInterceptor(new HttpHeaderInterceptor()) //
                .addNetworkInterceptor(new HttpCacheInterceptor()) //缓存拦截器
                // .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())  // https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
                // .hostnameVerifier(new SafeHostnameVerifier())
                .cache(cache); //添加缓存
    }

    public static Retrofit.Builder getRetrofitBuilder(String baseUrl) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        OkHttpClient okHttpClient = RetrofitService.getOkHttpClientBuilder().build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }
}
