package com.example.sourcewords.ui.learn.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.App;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.utils.PreferencesUtils;

import java.util.List;


public class LearnViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    @SuppressLint("StaticFieldLeak")
    private final WordRootRepository repository;
    private final MutableLiveData<Boolean> learnFlag;
    private final MutableLiveData<Integer> nowDay;//记录现在是所在年份的第几天

    private final static String LEARNWORDID = "WHAT I LREAN TODAY";
    private final static String ID = "TODAY WORDROOT";
    private final static String KEY_LEARNED = "key_wordroot_learned";


    //    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDM5ODI0NTMsImlhdCI6MTY0Mzg5NjA1MywidWlkIjoxN30.Lx1tkjDiTAgiG6GL65WMPA6dfFAKgLSPV0rqNqqoblU";

    public LearnViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
        repository = new WordRootRepository(App.getAppContext());
        learnFlag = new MutableLiveData<>(false);
        nowDay = new MutableLiveData<>();

    }

    public MutableLiveData<Integer> getNowDay(){
        return nowDay;
    }

    public MutableLiveData<Boolean> getLearnFlag() {
        return learnFlag;
    }



    public void updateRoot(int root_id) {
        repository.learnedTodayRoot(root_id);
    }

    public void insertRoots(WordRoot root) {
        repository.insertRoots(root);
    }

    public LiveData<List<WordRoot>> getSimilarWords(String query) {
        return repository.searchSimilar(query);
    }
    public LiveData<WordRoot> getWordRootById(int Id) {
        return repository.getWordRootById(Id);
    }
    public void saveWhatLearnedToday(int id){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(LEARNWORDID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID,id);
        editor.apply();

        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.putInt(PreferencesUtils.WORD_ROOT_TODAY, id);
        editor1.commit();

    }

    public int getWhatLearnedToday(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(LEARNWORDID, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ID, 0);
    }

    public WordRoot getRootByID(int id){
        return repository.getRootById(id);
    }

    public void saveFlag(boolean isLearned){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_TODAT);
        sp.put(KEY_LEARNED,isLearned);
    }

    public boolean getSaveFlag(){
        return SPUtils.getInstance(SPUtils.SP_LEARN_TODAT).getBoolean(KEY_LEARNED);
    }

}
