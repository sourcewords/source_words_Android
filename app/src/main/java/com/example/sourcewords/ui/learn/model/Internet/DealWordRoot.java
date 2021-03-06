package com.example.sourcewords.ui.learn.model.Internet;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

//TODO 网络请求部分
public interface DealWordRoot {
    @GET("roots/list")
    Observable<Test> getWordList(@Header("Authorization") String Authorization
            , @Query("verbose") String verbose);

    @PUT("user/plan")
    Observable<Void> learnToday(@Header("Authorization") String Authorization,
                                @Body Learned todayWord);

}

         //http://112.126.76.187:9999/api/v1/user/plan
         //http://112.126.76.187:9999/api/v1/user/plan
