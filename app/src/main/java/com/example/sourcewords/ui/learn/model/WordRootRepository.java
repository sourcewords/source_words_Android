package com.example.sourcewords.ui.learn.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sourcewords.ui.learn.model.Internet.DealWordRoot;
import com.example.sourcewords.ui.learn.model.Internet.Demo;
import com.example.sourcewords.ui.learn.model.Internet.Learned;
import com.example.sourcewords.ui.learn.model.Internet.Test;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordRootDao;
import com.example.sourcewords.ui.review.db.WordRootDatabase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//TODO 各种数据处理
public class WordRootRepository {
    private final WordRootDao wordRootDao;
    private static DealWordRoot dealWordRoot;
    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDQ0Nzk5NDksImlhdCI6MTY0NDM5MzU0OSwidWlkIjoxN30.3fA571_ktll7xL1aBSwEiAyoXc0QvmwdXt3XlyCw1VQ";
    private List<WordRoot> wordList;

    public WordRootRepository(Context mContext){
        final WordRootDatabase wordDatabase = WordRootDatabase.getDatabase();
        wordRootDao = wordDatabase.getWordDao();
        dealWordRoot = getRetrofit();
    }

    //模糊搜索
    public List<WordRoot> searchSimilar(String message){
        return wordRootDao.getWordRootsSimilar(message);
    }

    //
    public void insertRoots(WordRoot wordRoot){
        new InsertWordRoot(wordRootDao).execute(wordRoot);
    }

    //获取单词词根
    public WordRoot getWordRootById(int id){
        return wordRootDao.getWordRootById(id);
    }

    public static DealWordRoot getRetrofit() {
        if (dealWordRoot == null){
            synchronized(WordRootRepository.class){
                dealWordRoot = new Retrofit.Builder()
                        .baseUrl("http://112.126.76.187:9999/api/v1/")
                        .client(Demo.getInstance().build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(DealWordRoot.class);
            }
        }
        return dealWordRoot;
    }

    public void learnedTodayRoot(int root_id){
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

    static class InsertWordRoot extends AsyncTask<WordRoot,Void,Void>{
        private final WordRootDao dao;
        InsertWordRoot(WordRootDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(WordRoot... wordRoots) {
            dao.insertRoot(wordRoots);
            return null;
        }
    }
    //从远端拉入词根并插入本地数据库
    public void initWordRootList() {
        Log.d("Search!!!", "/////////////////////////");
        dealWordRoot.getWordList(Authorization, "true")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Test>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("something","wrong-------------------");
                    }

                    @Override
                    public void onNext(Test test) {
                        wordList = test.getData();
                        Log.d("接受","呼啦呼啦？？？？？？？？？？？？？？？？");
                        for(WordRoot wordRoot : wordList){
                            Log.d("test",wordRoot.getRoot() + wordRoot.getMeaning() + wordRoot.getVideo_url() );
                            insertRoots(wordRoot);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("更新！", "拉取数据网络错误......");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("完成！", "啦啦啦啦啦啦啦啦啦啦！！！！！！");
                    }

                });
    }


}
