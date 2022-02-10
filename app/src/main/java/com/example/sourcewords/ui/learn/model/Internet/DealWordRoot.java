package com.example.sourcewords.ui.learn.model.Internet;

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
            , @Query("kw") String keyword , @Query("verbose") String verbose);

    @PUT("roots/status")
    Call<Void> haveLearnedRoots(@Header("Authorization") String Authorization,
                                @Body Learned learned);

}
