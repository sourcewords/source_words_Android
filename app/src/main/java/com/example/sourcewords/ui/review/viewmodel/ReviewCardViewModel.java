package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;
import com.example.sourcewords.ui.review.view.reviewUtils.WordSample;
import com.example.sourcewords.utils.DateUtils;
import com.example.sourcewords.utils.PreferencesUtils;

import java.util.ArrayList;
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        int rootId = sharedPreferences.getInt(PreferencesUtils.WOOD_ROOT_TODAY, 0);
        Log.d("preferences", "" + rootId);
        newLearnedWords = mWordRepository.getNewWords(rootId);
        haveLearnedWords = mWordRepository.getLearnedWords(DateUtils.getData());
        reviewWords = new ArrayList<>(0);

        newLearnedCount = new MutableLiveData<>(newLearnedWords.size());
        haveLearnedCount = new MutableLiveData<>(haveLearnedWords.size());
        reviewCount = new MutableLiveData<>(priorityQueue.size());

        addInQueue();



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

    int i = 0;

    // 在优先队列里有单词到了时间复习则直接优先复习
    // 没有则先在新学的单词队列里找到新单词，再在以前学的单词的队列里寻找
    // 如果都没有，则从优先队列里强制拿单词
    public WordSample getNextWords() {
        WordSample wordSample = null;
        newLearnedCount.setValue(newLearnedWordsQueue.size());
        haveLearnedCount.setValue(haveLearnedWordsQueue.size());
        reviewCount.setValue(priorityQueue.size());
        if(priorityQueue.isEmpty() || priorityQueue.peek().getTime().compareTo(lastLearnTime) > 0)
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

    public void getPreWord() {
        if(historyStack.isEmpty()) return;
        WordSample wordSample = historyStack.pop();
        if(wordSample.getWord().getWord_info().getStatus() == wordSample.getStatus()) {
            return;
        }
        Log.d("pre", wordSample.toString() + "innerStatus :" + wordSample.getWord().getWord_info().getStatus());
//        switch (wordSample.getWord().getWord_info().getStatus()) {
//            case 0:
//                newLearnedCount.setValue(newLearnedWordsQueue.size() + 1);
//                newLearnedWordsQueue.offer(wordSample);
//                break;
//            case 1:
//                reviewCount.setValue(priorityQueue.size());
//                wordSample.setTime(DateUtils.getTime());
//                priorityQueue.offer(wordSample);
//                break;
//            case 2:
//                haveLearnedCount.setValue(haveLearnedWordsQueue.size() + 1);
//                haveLearnedWordsQueue.offer(wordSample);
//                break;
//        }

        switch (wordSample.getStatus()) {
            case 0:
                newLearnedWordsQueue.remove(wordSample);
//                newLearnedCount.setValue(newLearnedWordsQueue.size());
                break;
            case 1:
                priorityQueue.remove(wordSample);
//                reviewCount.setValue(priorityQueue.size());
                break;
            case 2:
                haveLearnedWordsQueue.offer(wordSample);
//                haveLearnedCount.setValue(haveLearnedWordsQueue.size());
                break;
        }
        newLearnedCount.setValue(newLearnedWordsQueue.size());
        haveLearnedCount.setValue(haveLearnedWordsQueue.size());
        reviewCount.setValue(priorityQueue.size());
        wordSample.setStatus(wordSample.getWord().getWord_info().getStatus());
        wordSampleMutableLiveData.setValue(wordSample);
        return;
    }
}
