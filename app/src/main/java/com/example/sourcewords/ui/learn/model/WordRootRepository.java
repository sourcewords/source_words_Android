package com.example.sourcewords.ui.learn.model;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.learn.model.Internet.DealWordRoot;
import com.example.sourcewords.ui.learn.model.Internet.Demo;
import com.example.sourcewords.ui.learn.model.Internet.Learned;
import com.example.sourcewords.ui.learn.model.Internet.Test;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordRootDao;
import com.example.sourcewords.ui.review.db.WordRootDatabase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
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
    //TODO 请在这里引入登录的token
    private final String Authorization = UserWrapper.getInstance().getToken();

    public WordRootRepository(){
        final WordRootDatabase wordDatabase = WordRootDatabase.getDatabase();
        wordRootDao = wordDatabase.getWordDao();
        dealWordRoot = getRetrofit();
    }

    public LiveData<List<WordRoot>> getAllWordRoots(){
       return wordRootDao.getAllWordRoot();
    }

    public WordRoot getRootById(int id){
        return wordRootDao.getRootById(id);
    }

    //模糊搜索
    public LiveData<List<WordRoot>> searchSimilar(String message){
        return wordRootDao.getWordRootsSimilar(message);
    }

    //
    public void insertRoots(WordRoot wordRoot){
        new InsertWordRoot(wordRootDao).execute(wordRoot);
    }

    //获取单词词根
    public LiveData<WordRoot> getWordRootById(int id){
        return wordRootDao.getWordRootByID(id);
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
    /*

    static class SearchWordRoot extends AsyncTask<Integer,Void,WordRoot>{
        private final WordRootDao dao;
        SearchWordRoot(WordRootDao dao){
            this.dao = dao;
        }
        @Override
        protected WordRoot doInBackground(Integer... integers) {
            int id= integers[0];
            return dao.getRootById(id);
        }
    }

    static class SearchWordRootSimilar extends AsyncTask<String,Void,List<WordRoot>>{
        private final WordRootDao dao;
        SearchWordRootSimilar(WordRootDao dao){
            this.dao = dao;
        }
        @Override
        protected List<WordRoot> doInBackground(String... strings) {
            String message = strings[0];
            //return dao.getWordRootsSimilar(message);
            return null;
        }
    }

     */

    public void whatILearnedToday(List<Integer> list){
        dealWordRoot.learnToday(Authorization,list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Void unused) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("上传网络请求","成功，开摆!");
                    }
                });
    }

    /*
    //从远端拉入词根并插入本地数据库
    public void initWordRootList(int level) {
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
                        Log.d("接受","呼啦呼啦？？？？？？？？？？？？？？？？");
                        //learnedRepository.initPlan(wordList,level);
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

     */
}
