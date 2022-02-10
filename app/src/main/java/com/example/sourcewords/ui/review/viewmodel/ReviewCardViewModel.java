package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.model.WordRepository;

import java.util.List;

public class ReviewCardViewModel extends AndroidViewModel {
    private WordRepository mWordRepository;
    private List<Word> listWordData;
    private LiveData<Word> wordLiveData;

    public ReviewCardViewModel(@NonNull Application application) {
        super(application);
        mWordRepository = new WordRepository();
        listWordData = mWordRepository.getWordRootTest(1).getWordlist();
    }


}
