package com.example.sourcewords.ui.review;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootStatus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface ReviewApi {
    @GET("/roots/list")
    Call<WordRoot> getWordRoot();
    @PUT("/roots/status")
    Call updateWordRootStatus(@Header("token") String token,
                              @Body WordRootStatus wordRootStatus);
}
