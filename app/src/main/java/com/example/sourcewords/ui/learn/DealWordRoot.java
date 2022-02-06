package com.example.sourcewords.ui.learn;

import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Query;

//TODO 网络请求部分
public interface DealWordRoot {
    @GET("roots/list")
    Observable<Test> getWordList(@Header("Authorization") String Authorization
            , @Query("kw") String keyword , @Query("verbose") String verbose);

}
