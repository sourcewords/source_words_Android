package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;

public class ReviewViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    public ReviewViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository();
    }

    public void Insert(WordRoot...wordRoots){mRepository.insert(wordRoots);}
    public void Delete(WordRoot...wordRoots){mRepository.delete(wordRoots);}
    public void Update(WordRoot...wordRoots){mRepository.update(wordRoots);}
}
