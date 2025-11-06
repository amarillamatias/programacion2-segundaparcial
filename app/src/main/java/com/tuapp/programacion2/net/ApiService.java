package com.tuapp.programacion2.net;

import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    String BASE = "https://webhook.site/";

    @Multipart @POST("anything")
    Call<ResponseBody> uploadClient(
        @Part MultipartBody.Part fotoCasa1,
        @Part MultipartBody.Part fotoCasa2,
        @Part MultipartBody.Part fotoCasa3,
        @Part("data") RequestBody json
    );

    @Multipart @POST("anything")
    Call<ResponseBody> uploadZip(
        @Part MultipartBody.Part filesZip,
        @Part("ci") RequestBody ci
    );

    @POST("anything")
    Call<ResponseBody> sendLogs(@Body List<com.tuapp.programacion2.data.LogApp> logs);
}
