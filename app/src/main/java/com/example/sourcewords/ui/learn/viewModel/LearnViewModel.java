package com.example.sourcewords.ui.learn.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.learn.model.LearnedRepository;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.ArrayList;
import java.util.List;


public class LearnViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final WordRootRepository rootRepository;
    private final LearnedRepository learnedRepository;
    private final MutableLiveData<Boolean> learnFlag;
    private final MutableLiveData<Integer> nowDay;//记录现在是所在年份的第几天
    private final MutableLiveData<Integer> nowPlan;
    private final static String KEY_PLAN = "key_plan";
    private final static String KEY_LEARNED = "key_wordroot_learned";
    private final static String KEY_LONG = "key_long";
    private final static String KEY_TIME = "key_today_time";


    public LearnViewModel(@NonNull Application application) {
        super(application);
        rootRepository = new WordRootRepository();
        learnedRepository = new LearnedRepository();
        learnFlag = new MutableLiveData<>(false);
        nowDay = new MutableLiveData<>();
        nowPlan = new MutableLiveData<>(1);

    }


    //TODO 获取计划
    public int getPlan() {
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_PLAN);
        return sp.getInt(KEY_PLAN, 1);
    }

    public void savePlan(int level){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_PLAN);
        sp.put(KEY_PLAN,level);
    }

    public MutableLiveData<Integer> getNowPlan() {
        return nowPlan;
    }

    public MutableLiveData<Integer> getNowDay() {
        return nowDay;
    }

    public MutableLiveData<Boolean> getLearnFlag() {
        return learnFlag;
    }


    //记录当前计划进行到第几天
    public int getLong(){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_LONG);
        Log.d("LearnPlan", String.valueOf(sp.getInt(KEY_LONG,1)));
        return sp.getInt(KEY_LONG,1);
    }

    //获取记录
    public void saveLong(int Long){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_LONG);
        sp.put(KEY_LONG,Long);
    }

    public LiveData<List<WordRoot>> getAllWordRoot(){
        return rootRepository.getAllWordRoots();
    }

    public void updateRoot(int root_id) {
        rootRepository.learnedTodayRoot(root_id);
    }

    public void insertRoots(WordRoot root) {
        rootRepository.insertRoots(root);
    }

    public LiveData<List<WordRoot>> getSimilarWords(String query) {
        return rootRepository.searchSimilar(query);
    }

    public LiveData<WordRoot> getWordRootById(int Id) {
        return rootRepository.getWordRootById(Id);
    }


    public WordRoot getRootByID(int id) {
        return rootRepository.getRootById(id);
    }

    public void saveFlag(boolean isLearned) {
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_TODAT);
        sp.put(KEY_LEARNED, isLearned);
    }

    public boolean getSaveFlag() {
        return SPUtils.getInstance(SPUtils.SP_LEARN_TODAT).getBoolean(KEY_LEARNED);
    }

    public boolean isToday(){
        Log.d("isToday","现在时间为" + getNow() +"储存的时间是" + getSaveFlag());
        return getNow() == getSaveDay();

    }

    private int getSaveDay(){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_TIME);
        return sp.getInt(KEY_TIME);
    }


    public void saveTime() {
        //获取存储的时间
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_TIME);
        sp.put(KEY_TIME, getNow());
    }

    @SuppressLint("SimpleDateFormat")
    public int getNow() {
        Time t = new Time();
        t.setToNow();
        int day = t.monthDay;
        int hour = t.hour;
        Log.d("Time Now",day + "时间" + hour);
        return hour < 4 ? day - 1 : day;
    }

    public void whatILearnedToday(List<Integer> ids){
        rootRepository.whatILearnedToday(ids);
    }

}
