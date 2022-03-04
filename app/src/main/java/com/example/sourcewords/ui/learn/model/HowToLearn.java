package com.example.sourcewords.ui.learn.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;

import java.util.List;

//TODO 所思的学习算法
@RequiresApi(api = Build.VERSION_CODES.N)
public class HowToLearn {
    private static WordRepository wordRepository;
    private LiveData<List<WordRoot>> wordLiveData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public HowToLearn(){
        wordRepository = new WordRepository();
    }

    //第几天就是第几个词根

}
