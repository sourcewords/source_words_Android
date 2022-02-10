package com.example.sourcewords.ui.learn.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.learn.model.Internet.DealWordRoot;
import com.example.sourcewords.ui.learn.model.Internet.Learned;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootDao;
import com.example.sourcewords.ui.review.db.WordDatabase;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//TODO 各种数据处理
public class WordRootRepository {
    private final WordRootDao wordRootDao;
    private static Retrofit retrofit;
    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDQ0MjIwMTgsImlhdCI6MTY0NDMzNTYxOCwidWlkIjoxN30.gHUsZwfKjTiPoajaSYSTn8FqVIRO7gF09GO96ESxJCY";

    public WordRootRepository(Context mContext){
        final WordDatabase wordDatabase = WordDatabase.getDatabase(mContext);
        wordRootDao = wordDatabase.getWordDao();
    }
    //获取全部的词根
    public LiveData<List<WordRoot>> getAllWordRoots(){
        return wordRootDao.getAllWordRoot();
    }

    //模糊搜索
    public List<WordRoot> searchSimilar(String message){
        return wordRootDao.getWordRootsSimilar(message);
    }

    //获取单词词根
    public WordRoot getWordRootById(int id){
        return wordRootDao.getWordRootById(id);
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null){
            synchronized(WordRootRepository.class){
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://112.126.76.187:9999/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    public void learnedTodayRoot(int root_id){
        retrofit = getRetrofit();
        DealWordRoot dealWordRoot = retrofit.create(DealWordRoot.class);
        Learned learned = new Learned(root_id,1);
        dealWordRoot.haveLearnedRoots(Authorization,learned).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("学完词根。。。","完成更新///////////////////////////");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    /*奇怪的异步类
    static class SearchSimilar extends AsyncTask<String,Void,Void>{
        private final WordRootDao dao;

        SearchSimilar(WordRootDao wordRootDao){
            this.dao = wordRootDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String message = strings[0];
            dao.getWordRootsSimilar(message);
            return null;
        }
    }

    static class GetWordRootById extends AsyncTask<Integer,Void,Void>{
        private final WordRootDao dao;

        GetWordRootById(WordRootDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Integer... integers) {
            int id = integers[0];
            dao.getWordRootById(id);
            return null;
        }
    }
     */


    /*
    public List<Test.DataBean> getWordList(String keyWord) {
        Log.d("Search!!!",keyWord);
        dealWordRoot.getWordList(Authorization, keyWord,"false")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Test>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Test test) {
                        wordRootList = test.getData();
                        for(Test.DataBean a : wordRootList){
                            Log.d("why!!!",a.getRoot() + a.getMeaning());
                        }
                        Log.d("Test","成功！");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error","失败了..............................");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return wordRootList;
    }*/
}
