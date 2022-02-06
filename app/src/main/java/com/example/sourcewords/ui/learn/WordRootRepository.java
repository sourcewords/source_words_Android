package com.example.sourcewords.ui.learn;

import android.content.Context;

import androidx.lifecycle.LiveData;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootDao;
import com.example.sourcewords.ui.review.db.WordDatabase;
import java.util.List;

public class WordRootRepository {
    private final WordRootDao wordRootDao;
    //    private static Retrofit retrofit;

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
/*网络请求部分，需要用的话以后再加
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
