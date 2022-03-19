package com.example.sourcewords.ui.learn.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.learn.model.LearnedRepository;
import com.example.sourcewords.ui.learn.model.WordRootDBCallBack;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class LearnViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final WordRootRepository rootRepository;
    private final MutableLiveData<Boolean> learnFlag;
    private final MutableLiveData<Integer> nowDay;//记录现在是所在年份的第几天
    private final MutableLiveData<Integer> nowPlan;
    private final static String KEY_PLAN = "key_plan";
    private final static String KEY_LEARNED = "key_wordroot_learned";
    private final static String KEY_LONG = "key_long";
    private final static String KEY_TIME = "key_today_time";//记录登录时间
    private final static String KEY_LAST  = "key_last";//记录昨天学的最后一个词根
    private final static String KEY_MAKE_PLAN = "key_make_plan";


    public LearnViewModel(@NonNull Application application) {
        super(application);
        rootRepository = new WordRootRepository();
        learnFlag = new MutableLiveData<>(false);
        nowDay = new MutableLiveData<>();
        nowPlan = new MutableLiveData<>(1);

    }


    //TODO 获取计划的等级，如4级
    public int getPlan() {
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_PLAN);
        return sp.getInt(KEY_PLAN, 1);
    }

    public void savePlan(int level){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_PLAN);
        sp.put(KEY_PLAN,level);
    }


    public int getLastPlan(){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_Last);
        return sp.getInt(KEY_LAST,0);
    }
    //TODO 储存上次计划进行到第几天
    public void saveLastPlan(int yesterday){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_Last);
        sp.put(KEY_LAST, yesterday);
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


    //TODO获取当前学到的词根的id
    public int getLong(){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_LONG);
        Log.d("LearnPlan", String.valueOf(sp.getInt(KEY_LONG,1)));
        return sp.getInt(KEY_LONG,1);
    }

    //储存当前学过的词根id
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

    //是否今天是否学完
    public void saveFlag(boolean isLearned) {
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_TODAY);
        sp.put(KEY_LEARNED, isLearned);
    }

    public boolean getSaveFlag() {
        return SPUtils.getInstance(SPUtils.SP_LEARN_TODAY).getBoolean(KEY_LEARNED);
    }

    public boolean isToday(){
        Log.d("isToday","现在时间为" + getNow() +"储存的时间是" + getSaveFlag());
        return getNow() == getSaveDay();

    }

    public int getSaveDay(){
        //获取存储的时间
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_TIME);
        return sp.getInt(KEY_TIME);
    }


    public void saveTime() {
        //存储当前的时间
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

    public LiveData<List<WordRoot>> getAll(){
        return rootRepository.getAllWordRoots();
    }

    //储存记录指定计划的时间
    public void saveMakePlan(){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_MAKE_PLAN);
        sp.put(KEY_MAKE_PLAN,getTodayTime());
    }

    public String getMakePlan(){
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_MAKE_PLAN);
        return sp.getString(KEY_MAKE_PLAN,getTodayTime());
    }

    private String getTodayTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    //计算今天应该是计划的第几天(每天都做的情况下)
    public int HowLongPlan(){
        String[] start = getMakePlan().split("-");
        String[] now = getTodayTime().split("-");
        int[] months = {31,28,31,30,31,30,31,31,30,31,30,31};
        int sum = 0;
        int y = Integer.parseInt(now[0]) - Integer.parseInt(start[0]);
        int end = Integer.parseInt(now[1]);
        if(end - Integer.parseInt(start[1]) < 0){
            end+=12;
            y--;
        }
        for(int i = Integer.parseInt(start[1]); i < end ;i++ ){
            sum+=months[(i -1)%12];
        }
        return sum + y*365 + Integer.parseInt(now[2]) - Integer.parseInt(start[2]) + 1;
    }

    public void getLikelyWordRoot(String keyWords, WordRootDBCallBack wordRootDBCallBack){rootRepository.getLikelyWordRoots(keyWords, wordRootDBCallBack);}
}
