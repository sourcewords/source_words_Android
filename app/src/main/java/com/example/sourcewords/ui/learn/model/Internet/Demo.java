package com.example.sourcewords.ui.learn.model.Internet;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

//TODO 网络日志相关
public class Demo {
    private final int Default_Time = 60;
    private static OkHttpClient.Builder builder;
    public static OkHttpClient.Builder getInstance(){
        if(builder == null){
            builder = new OkHttpClient.Builder();
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder;
    }

    public void set(){
        builder.connectTimeout(Default_Time, TimeUnit.SECONDS);
    }
}
