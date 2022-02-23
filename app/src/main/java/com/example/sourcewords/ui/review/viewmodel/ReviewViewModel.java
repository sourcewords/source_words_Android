package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {
    private WordRepository mWordRepository;
    public ReviewViewModel(@NonNull Application application) {
        super(application);
        mWordRepository = new WordRepository();
    }

    public void Insert(WordRoot...wordRoots){
        mWordRepository.Insert(wordRoots);
    }

    public void Delete(WordRoot...wordRoots){
        mWordRepository.Delete(wordRoots);
    }

    public void Update(WordRoot...wordRoots){
        mWordRepository.Update(wordRoots);
    }

    public WordRoot getWordRoot(int id){ return mWordRepository.getWordRootByID(id); }

    public WordRoot getWordRootTest(int id){return mWordRepository.getWordRootTest2(id);}

    //以前学习的单词列表
    public List<Word> getTobeReview() { return mWordRepository.getTobeReview(); }

    //今天需要再次复习的单词列表
    public List<Word> getReviewAgainToday() { return mWordRepository.getReviewAgainToday(); }

    //今天新学的单词列表
    public List<Word> getNewWordsToday() { return mWordRepository.getNewWordsToday(); }

}
