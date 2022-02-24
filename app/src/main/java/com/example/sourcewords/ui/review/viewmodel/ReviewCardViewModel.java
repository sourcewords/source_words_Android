package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.model.WordRepository;
import com.example.sourcewords.utils.DateUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ReviewCardViewModel extends AndroidViewModel {
    private WordRepository mWordRepository;
    private List<Word> newLearnedWords;
    private List<Word> haveLearnedWords;
    private List<Word> reviewWords;
    private int newLearnedCount;
    private int haveLearnedCount;
    private int reviewCount;
    private int flag = 0;

    private Queue<Word> newLearnedWordsQueue;
    private Queue<Word> haveLearnedWordsQueue;
    private Queue<Word> reviewWordsQueue;


    public ReviewCardViewModel(@NonNull Application application) {
        super(application);
        mWordRepository = new WordRepository();
        initData();
    }

    private void initData() {
        newLearnedWords = mWordRepository.getNewWords();
        haveLearnedWords = mWordRepository.getCurrentWords();
        reviewWords = mWordRepository.getLearnedWords(DateUtils.getTime());

        addInQueue();

        newLearnedCount = newLearnedWordsQueue.size();
        haveLearnedCount = haveLearnedWordsQueue.size();
        reviewCount = reviewWordsQueue.size();

    }

    public int getNewLearnedCount() {
        return newLearnedCount;
    }

    public int getHaveLearnedCount(){
        return haveLearnedCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int currentFlag() {
        return flag;
    }

    public Word getNextWords() {
        if(newLearnedWords != null && newLearnedWordsQueue.size() != 0) {
            flag = 0;
            newLearnedCount--;
            return newLearnedWordsQueue.poll();
        }
        else if(haveLearnedWords != null && haveLearnedWordsQueue.size() != 0) {
            flag = 1;
            haveLearnedCount--;
            return haveLearnedWordsQueue.poll();
        }
        else if(reviewWords != null && reviewWordsQueue.size() != 0) {
            flag = 2;
            reviewCount--;
            return reviewWordsQueue.poll();
        }
        else {
            initData();
            return getNextWords();
        }
    }

    public Word getPreWord(Word word, int flag) {
        Word pre = null;
        switch (flag) {
            case 0:
                for(int i = 0; i<newLearnedWords.size(); i++) {
                    if(newLearnedWords.get(i).getId() == word.getId()) {
                        break;
                    }
                    else {
                        pre = newLearnedWords.get(i);
                        newLearnedWords.remove(pre);
                    }
                }
                break;
            case 1:
                for(int i = 0; i<haveLearnedWords.size(); i++) {
                    if(haveLearnedWords.get(i).getId() == word.getId()) {
                        break;
                    }
                    else {
                        pre = haveLearnedWords.get(i);
                        haveLearnedWords.remove(pre);
                    }
                }
                break;
            case 2:
                for(int i = 0; i<reviewWords.size(); i++) {
                    if(reviewWords.get(i).getId() == word.getId()) {
                        break;
                    }
                    else {
                        pre = reviewWords.get(i);
                        reviewWords.remove(pre);
                    }
                }
                break;
        }
        addInQueue();
        if(pre == null) {
            switch (flag) {
                case 0:
                    newLearnedWordsQueue.poll();
                    break;

                case 1:
                    haveLearnedWordsQueue.poll();
                    break;

                case 2:
                    reviewWordsQueue.poll();
                    break;
            }
            return word;
        }
        else return pre;
    }

    private void addInQueue() {
        newLearnedWordsQueue = new LinkedList<>();
        haveLearnedWordsQueue = new LinkedList<>();
        reviewWordsQueue = new LinkedList<>();

        for(Word w : newLearnedWords) {
            newLearnedWordsQueue.offer(w);
        }
        for(Word w : haveLearnedWords) {
            haveLearnedWordsQueue.offer(w);
        }
        for(Word w : reviewWords) {
            reviewWordsQueue.offer(w);
        }
    }

    public void insert(Word word) {
        mWordRepository.insert(word);
    }
}
