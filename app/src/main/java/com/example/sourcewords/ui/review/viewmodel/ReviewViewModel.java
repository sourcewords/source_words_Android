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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ReviewViewModel(@NonNull Application application) {
        super(application);
        mWordRepository = new WordRepository();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insert(Word...words) {
        mWordRepository.insert(words);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Word search(int id) {
        return mWordRepository.search(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Insert(WordRoot...wordRoots){
        mWordRepository.Insert(wordRoots);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Delete(WordRoot...wordRoots){
        mWordRepository.Delete(wordRoots);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Update(WordRoot...wordRoots){
        mWordRepository.Update(wordRoots);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WordRoot getWordRoot(int id){ return mWordRepository.getWordRootByID(id); }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WordRoot getWordRootTest(int id){return mWordRepository.getWordRootTest2(id);}

}
