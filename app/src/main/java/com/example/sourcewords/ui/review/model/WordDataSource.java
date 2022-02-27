package com.example.sourcewords.ui.review.model;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordDataSource {
    private static WordDataSource INSTANCE;

    private final String token = UserWrapper.getInstance().getToken();

    public static WordDataSource getInstance(){
        if(INSTANCE == null){
            synchronized (WordDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new WordDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private WordDataSource(){};

    public void getRoots(){
        NetUtil.getInstance().getApi().getWordRoot().enqueue(new Callback<WordRoot>() {
            @Override
            public void onResponse(Call<WordRoot> call, Response<WordRoot> response) {

            }

            @Override
            public void onFailure(Call<WordRoot> call, Throwable t) {

            }
        });
    }
}
