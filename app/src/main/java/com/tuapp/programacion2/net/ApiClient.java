package com.tuapp.programacion2.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiService api;
    public static ApiService get(){
        if(api==null){
            HttpLoggingInterceptor log = new HttpLoggingInterceptor();
            log.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(log).build();
            Retrofit r = new Retrofit.Builder()
                .baseUrl(ApiService.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
            api = r.create(ApiService.class);
        }
        return api;
    }
}
