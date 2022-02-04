package com.example.sourcewords.ui.learn;

import android.content.Context;
import android.util.Log;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ViewModel {
    private Context mContext;
    private WordRootRepository repository;
    private DealWordRoot dealWordRoot;
    private List<Test.DataBean> wordRootList;
    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDM5ODI0NTMsImlhdCI6MTY0Mzg5NjA1MywidWlkIjoxN30.Lx1tkjDiTAgiG6GL65WMPA6dfFAKgLSPV0rqNqqoblU";

    ViewModel(Context context) {
        this.mContext = context;
        repository = new WordRootRepository(mContext);
        dealWordRoot = WordRootRepository.getRetrofit().create(DealWordRoot.class);
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
    }
}
