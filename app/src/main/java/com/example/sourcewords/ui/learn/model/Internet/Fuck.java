package com.example.sourcewords.ui.learn.model.Internet;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.ArrayList;
import java.util.List;


public class Fuck {
    private WordRootRepository repository;
    private List<WordRoot> list;

    public Fuck(WordRootRepository repository){
        this.repository = repository;
    }
    public void AsyncTask(String s, MutableLiveData<List<WordRoot>> wordRoots){
        list = new ArrayList<>();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x8848){
                    wordRoots.setValue(list);
                    Log.d("this","Done");
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = repository.searchSimilar(s);
                    handler.sendEmptyMessage(0x8848);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
