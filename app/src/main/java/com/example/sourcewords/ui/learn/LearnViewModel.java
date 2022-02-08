package com.example.sourcewords.ui.learn;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;


public class LearnViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private final WordRootRepository repository;
//    private DealWordRoot dealWordRoot;
    private final LiveData<List<WordRoot>> wordRootLiveData;
    //    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDM5ODI0NTMsImlhdCI6MTY0Mzg5NjA1MywidWlkIjoxN30.Lx1tkjDiTAgiG6GL65WMPA6dfFAKgLSPV0rqNqqoblU";

    LearnViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
        repository = new WordRootRepository(mContext);
        //dealWordRoot = WordRootRepository.getRetrofit().create(DealWordRoot.class);
        wordRootLiveData = repository.getAllWordRoots();
    }

    public LiveData<List<WordRoot>> getAllWordRoots(){
        return  wordRootLiveData;
    }

    public List<WordRoot> getList(String query){
        return repository.searchSimilar(query);
    }

    public WordRoot getWordRoot(int id){
        return repository.getWordRootById(id);
    }

    public void updateRoot(int root_id){
        repository.learnedTodayRoot(root_id);
    }




}
