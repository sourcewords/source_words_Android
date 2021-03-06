package com.example.sourcewords.ui.review.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.savedstate.SavedStateRegistry;

import com.example.sourcewords.App;
import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordCardState;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;
import com.example.sourcewords.ui.review.view.reviewUtils.WordSample;
import com.example.sourcewords.utils.DateUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ReviewCardViewModel extends AndroidViewModel {
    private WordRepository mWordRepository;
    private List<Word> newLearnedWords;
    private List<Word> haveLearnedWords;
    private List<Word> reviewWords;
    private MutableLiveData<Integer> newLearnedCount;
    private MutableLiveData<Integer> haveLearnedCount;
    private MutableLiveData<Integer> reviewCount;

    private MutableLiveData<WordSample> wordSampleMutableLiveData;
    // 用来存储所有的背过的不需要复习的单词的状态,这时单词的下次复习时间应该都是日期状态
    private HashMap<Integer, WordSample> wordPool = new HashMap<>();
    // 上次的学习时间 用hh：mm表示
    private String lastLearnTime;
    // 单词的复习历史
    private Stack<WordSample> historyStack = new Stack<>();

    // 单词的按时间排序的优先队列
    private PriorityQueue<WordSample> priorityQueue = new PriorityQueue<>();
    // 今日第一次学或者复习的单词队列
    private Queue<WordSample> newLearnedWordsQueue;
    private Queue<WordSample> haveLearnedWordsQueue;


    public List<Word> getNewLearnedWords() {
        return newLearnedWords;
    }

    public void setNewLearnedWords(List<Word> newLearnedWords) {
        this.newLearnedWords = newLearnedWords;
    }

    public List<Word> getHaveLearnedWords() {
        return haveLearnedWords;
    }

    public void setHaveLearnedWords(List<Word> haveLearnedWords) {
        this.haveLearnedWords = haveLearnedWords;
    }

    public List<Word> getReviewWords() {
        return reviewWords;
    }

    public void setReviewWords(List<Word> reviewWords) {
        this.reviewWords = reviewWords;
    }

    public void setNewLearnedCount(MutableLiveData<Integer> newLearnedCount) {
        this.newLearnedCount = newLearnedCount;
    }

    public void setHaveLearnedCount(MutableLiveData<Integer> haveLearnedCount) {
        this.haveLearnedCount = haveLearnedCount;
    }

    public void setReviewCount(MutableLiveData<Integer> reviewCount) {
        this.reviewCount = reviewCount;
    }

    public MutableLiveData<WordSample> getWordSampleMutableLiveData() {
        return wordSampleMutableLiveData;
    }

    public void setWordSampleMutableLiveData(MutableLiveData<WordSample> wordSampleMutableLiveData) {
        this.wordSampleMutableLiveData = wordSampleMutableLiveData;
    }


    public Queue<WordSample> getNewLearnedWordsQueue() {
        return newLearnedWordsQueue;
    }

    public void setNewLearnedWordsQueue(Queue<WordSample> newLearnedWordsQueue) {
        this.newLearnedWordsQueue = newLearnedWordsQueue;
    }

    public Queue<WordSample> getHaveLearnedWordsQueue() {
        return haveLearnedWordsQueue;
    }

    public void setHaveLearnedWordsQueue(Queue<WordSample> haveLearnedWordsQueue) {
        this.haveLearnedWordsQueue = haveLearnedWordsQueue;
    }


    public ReviewCardViewModel(@NonNull Application application) {
        super(application);
        mWordRepository = new WordRepository();
        initData();
        wordSampleMutableLiveData = new MutableLiveData<>();
    }

    public void initData() {
        reviewWords = new ArrayList<>(0);
        newLearnedWords =new ArrayList<>(0);
        haveLearnedWords = mWordRepository.getLearnedWords(DateUtils.getDate());
        newLearnedCount = new MutableLiveData<>(newLearnedWords.size());
        haveLearnedCount = new MutableLiveData<>(haveLearnedWords.size());
        reviewCount = new MutableLiveData<>(priorityQueue.size());
        addInQueue();
    }

    //新增过滤的效果，按照所选的计划等级筛选
    public void initData(int rootId) {
        newLearnedWords = mWordRepository.getNewWords(rootId);
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_PLAN);
        int level = sp.getInt("key_plan",1);
        List<Word> res = new ArrayList<>();
        for (Word word : newLearnedWords) {
            if (word.getWord_info().getExam_grading().get(level - 1)) {
                res.add(word);
            }
        }
        for (Word w : res) {
            WordSample wordSample = new WordSample(w, w.getWord_info().getStatus(),"");
            newLearnedWordsQueue.offer(wordSample);
        }
        newLearnedCount.setValue(newLearnedWordsQueue.size());

//        String target = sharedPreferences.getString(PreferencesUtils.WORD_ROOT_TODAY,"[0]");
//        Log.d("target", target);
//        List<Integer> rootIds = new ArrayList<>();
//
//        try{
//            JSONArray jsonArray = new JSONArray(target);
//            for(int i = 0 ; i < jsonArray.length() ; i++){
//                rootIds.add((Integer) jsonArray.get(i));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        for(int rootId : rootIds){
//            Log.d("preferences", "" + rootId);
//
//        }
    }

    public MutableLiveData<Integer> getNewLearnedCount() {
        return newLearnedCount;
    }

    public MutableLiveData<Integer> getHaveLearnedCount() {
        return haveLearnedCount;
    }

    public MutableLiveData<Integer> getReviewCount() {
        return reviewCount;
    }


    // 在优先队列里有单词到了时间复习则直接优先复习
    // 没有则先在新学的单词队列里找到新单词，再在以前学的单词的队列里寻找
    // 如果都没有，则从优先队列里强制拿单词
    public WordSample getNextWords() {
        WordSample wordSample = null;
        newLearnedCount.setValue(newLearnedWordsQueue.size());
        haveLearnedCount.setValue(haveLearnedWordsQueue.size());
        reviewCount.setValue(priorityQueue.size());

        // 队列当前第一个单词的时间和此刻时间的差值
        long elapsed = 0l;
        if(!priorityQueue.isEmpty()) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date t1 = null;
            try {
                t1 = sdf.parse(lastLearnTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date t2 = null;
            try {
                t2 = sdf.parse(priorityQueue.peek().getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert t2 != null;
            assert t1 != null;
            elapsed = t2.getTime() - t1.getTime();
            Log.d("time",lastLearnTime + " " + priorityQueue.peek().getTime());
        }

        if(priorityQueue.isEmpty() || elapsed > 0)
        if(!newLearnedWordsQueue.isEmpty()) {
            Log.d("nq", "" + newLearnedWordsQueue.size());
            wordSample = newLearnedWordsQueue.poll();
        }
        else if(!haveLearnedWordsQueue.isEmpty()) {
            Log.d("hq", "" + haveLearnedWordsQueue.size());
            wordSample = haveLearnedWordsQueue.poll();
        }
        else {
            Log.d("pq", "" + priorityQueue.size());
            wordSample = priorityQueue.poll();
        }
        else wordSample = priorityQueue.poll();

        return wordSample;
    }

    private void refreshNewLearnedWords() {
        newLearnedWords = new ArrayList<>();
        while(!newLearnedWordsQueue.isEmpty()) {
            newLearnedWords.add(newLearnedWordsQueue.poll().getWord());
        }
    }


    private void addInQueue() {
        newLearnedWordsQueue = new LinkedList<>();
        haveLearnedWordsQueue = new LinkedList<>();

        for (Word w : newLearnedWords) {
            WordSample wordSample = new WordSample(w, w.getWord_info().getStatus(),"");
            newLearnedWordsQueue.offer(wordSample);
        }
        for (Word w : haveLearnedWords) {
            WordSample wordSample = new WordSample(w, w.getWord_info().getStatus(), "");
            haveLearnedWordsQueue.offer(wordSample);
        }

    }

    public HashMap<Integer, WordSample> getWordPool() {
        return wordPool;
    }

    public void setWordPool(HashMap<Integer, WordSample> wordPool) {
        this.wordPool = wordPool;
    }

    public String getLastLearnTime() {
        return lastLearnTime;
    }

    public void setLastLearnTime(String lastLearnTime) {
        this.lastLearnTime = lastLearnTime;
    }

    public Stack<WordSample> getHistoryStack() {
        return historyStack;
    }

    public void setHistoryStack(Stack<WordSample> historyStack) {
        this.historyStack = historyStack;
    }

    public PriorityQueue<WordSample> getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(PriorityQueue<WordSample> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }


    public void insert(Word... words) {
        mWordRepository.insert(words);
    }

    public LiveData<List<Word>> getAllWord() {
        return mWordRepository.getAllWords();
    }

    public LiveData<List<WordRoot>> getAllWordRoot() {
        return mWordRepository.getAllWordRoot();
    }

    public LiveData<List<SingleWord>> getAllSingleWord() {
        return mWordRepository.getAllSingleWord();
    }

    public SingleWord getSingleWordById(int id) {
        return mWordRepository.getSingleWordById(id);
    }

    public void insert(SingleWord...singleWords) {
        mWordRepository.insert(singleWords);
    }

    public MutableLiveData<WordSample> getNextWordSample() {
        wordSampleMutableLiveData.setValue(getNextWords());
        return wordSampleMutableLiveData;
    }

    public void getPreWord(int status) {
        if(historyStack.isEmpty()) return;
        WordSample wordSample = historyStack.pop();

        // 从当前各个队列中清空，但其实只要清空在优先队列的就可以
        switch (wordSample.getStatus()) {
            case 0:
                break;
            case 1:
                priorityQueue.remove(wordSample);
                break;
            case 2:
                break;
        }
        newLearnedCount.setValue(newLearnedWordsQueue.size());
        haveLearnedCount.setValue(haveLearnedWordsQueue.size());
        reviewCount.setValue(priorityQueue.size());
        switch (status) {
            case 0 :
                newLearnedCount.setValue(newLearnedWordsQueue.size() + 1);
                break;
            case 2 :
                haveLearnedCount.setValue(haveLearnedWordsQueue.size() + 1);
                break;
        }
        wordSample.setStatus(wordSample.getWord().getWord_info().getStatus());
        wordSampleMutableLiveData.setValue(wordSample);
        return;
    }

    public void loadAllWordsToDataBase() {
        mWordRepository.loadAllWordsToDataBase(wordPool);
    }

    public void initFromDataBase(String date) {
        WordCardState wordCardState = mWordRepository.getWordCardState(date);
        if(wordCardState == null) return;
        this.newLearnedWords = wordCardState.getNewLearnedWords();
        this.haveLearnedWords = wordCardState.getHaveLearnedWords();
        this.reviewWords = wordCardState.getReviewWords();
        this.newLearnedCount.setValue(wordCardState.getNewLearnedCount());
        this.haveLearnedCount.setValue(wordCardState.getHaveLearnedCount());
        this.reviewCount.setValue(wordCardState.getReviewCount());
        this.wordSampleMutableLiveData.setValue(wordCardState.getWordSample());
        this.wordPool = wordCardState.getWordPool();
        this.lastLearnTime = wordCardState.getLastLearnTime();
        this.historyStack = wordCardState.getHistoryStack();
        this.priorityQueue = wordCardState.getPriorityQueue();
        this.newLearnedWordsQueue = wordCardState.getNewLearnedWordsQueue();
        this.haveLearnedWordsQueue = wordCardState.getHaveLearnedWordsQueue();
    }

    public void saveWordCardState(WordCardState wordCardState) {
        mWordRepository.insertWordCardState(wordCardState);
    }

    public void deleteWordCardState() {
        mWordRepository.deleteWordCardState();
    }

    public void uploadReviewCardState(String s) {
        mWordRepository.uploadReviewCardState(s);
    }

    public void downloadWordCardState() {
        mWordRepository.downloadWordCardState(this);
    }

    public void refreshViewModel(WordCardState wordCardState) {
        this.newLearnedWords = wordCardState.getNewLearnedWords();
        this.haveLearnedWords = wordCardState.getHaveLearnedWords();
        this.reviewWords = wordCardState.getReviewWords();
        this.newLearnedCount.setValue(wordCardState.getNewLearnedCount());
        this.haveLearnedCount.setValue(wordCardState.getHaveLearnedCount());
        this.reviewCount.setValue(wordCardState.getReviewCount());
        this.wordSampleMutableLiveData.setValue(wordCardState.getWordSample());
        this.wordPool = wordCardState.getWordPool();
        this.lastLearnTime = wordCardState.getLastLearnTime();
        this.historyStack = wordCardState.getHistoryStack();
        this.priorityQueue = wordCardState.getPriorityQueue();
        this.newLearnedWordsQueue = wordCardState.getNewLearnedWordsQueue();
        this.haveLearnedWordsQueue = wordCardState.getHaveLearnedWordsQueue();
    }
}
