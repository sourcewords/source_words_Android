package com.example.sourcewords.commonUtils;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetUtil {

    private OkHttpClient client;
    private RetrofitApi api;
    private Gson gson;
    private static final String baseUrl = "http://127.0.0.1:4523/mock/615381/";

    private NetUtil() {
        gson = new Gson();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        api = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build()
                .create(RetrofitApi.class);
    }

    public Gson getGson() {
        return gson;
    }

    public static NetUtil getInstance() {
        return NetUtilHolder.INSTANCE;
    }

    private static class NetUtilHolder {
        private static final NetUtil INSTANCE = new NetUtil();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public RetrofitApi getApi() {
        return api;
    }
}
