package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;

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

}
