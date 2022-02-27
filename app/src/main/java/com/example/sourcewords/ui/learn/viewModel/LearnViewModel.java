package com.example.sourcewords.ui.learn.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;
import java.util.Objects;


public class LearnViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private final WordRootRepository repository;
    private final static String LEARNWORDID = "WHAT I LREAN TODAY";
    private final static String ID = "TODAY WORDROOT";
    //    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDM5ODI0NTMsImlhdCI6MTY0Mzg5NjA1MywidWlkIjoxN30.Lx1tkjDiTAgiG6GL65WMPA6dfFAKgLSPV0rqNqqoblU";

    public LearnViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
        repository = new WordRootRepository(App.getAppContext());

    }

    public List<WordRoot> getSimilarWords(String query) {
        return repository.searchSimilar(query);
    }

    public WordRoot getWordRootById(int Id) {
        return repository.getWordRootById(Id);
    }

    public void saveWhatLearnedToday(int id){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(LEARNWORDID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID,id);
        editor.apply();
    }

    public void updateRoot(int root_id) {
        repository.learnedTodayRoot(root_id);
    }

    public void insertRoots(WordRoot root) {
        repository.insertRoots(root);
    }


}
