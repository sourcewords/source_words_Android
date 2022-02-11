package com.example.sourcewords.ui.learn.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.ui.learn.model.Internet.Fuck;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;


public class LearnViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private final WordRootRepository repository;
    private final Fuck f;
    //    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDM5ODI0NTMsImlhdCI6MTY0Mzg5NjA1MywidWlkIjoxN30.Lx1tkjDiTAgiG6GL65WMPA6dfFAKgLSPV0rqNqqoblU";

    public LearnViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
        repository = new WordRootRepository(mContext);
        f = new Fuck(repository);

    }




    public void getList(String query,MutableLiveData<List<WordRoot>> list){
        f.AsyncTask(query,list);
    }

    public WordRoot getWordRoot(int id){
        return repository.getWordRootById(id);
    }

    public void updateRoot(int root_id){
        repository.learnedTodayRoot(root_id);
    }

    public void insertRoots(WordRoot root){
        repository.insertRoots(root);
    }




}
