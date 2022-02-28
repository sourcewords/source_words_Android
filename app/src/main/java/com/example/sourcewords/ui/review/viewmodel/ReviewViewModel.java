package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;
public class ReviewViewModel extends AndroidViewModel {
    private WordRepository mWordRepository;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        mWordRepository = new WordRepository();
    }

    public void insert(Word...words) {
        mWordRepository.insert(words);
    }

    public Word search(int id) {
        return mWordRepository.search(id);
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


}
