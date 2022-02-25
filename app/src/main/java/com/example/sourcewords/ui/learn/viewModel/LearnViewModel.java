package com.example.sourcewords.ui.learn.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class LearnViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private final WordRootRepository repository;
    private List<WordRoot> list = new ArrayList<>();
    private static WordRoot wordRoot;
    //    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDM5ODI0NTMsImlhdCI6MTY0Mzg5NjA1MywidWlkIjoxN30.Lx1tkjDiTAgiG6GL65WMPA6dfFAKgLSPV0rqNqqoblU";

    public LearnViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
        repository = new WordRootRepository(App.getAppContext());

    }




    public void getList(String query,MutableLiveData<List<WordRoot>> wordRoots){
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
                        list = repository.searchSimilar(query);
                        handler.sendEmptyMessage(0x8848);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


    }

    public void getWordRoot(int id, TextView textView, WordsAdapter adapter, VideoView videoView){
        Handler handler = new Handler(){
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x6666){
                    textView.setText(wordRoot.getRoot());
                    adapter.setList(wordRoot.getWordlist());
                    videoView.setVideoURI(Uri.parse(wordRoot.getVideo_url()));
                    Log.d("this","Done");
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    wordRoot = repository.getWordRootById(id);
                    handler.sendEmptyMessage(0x6666);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateRoot(int root_id){
        repository.learnedTodayRoot(root_id);
    }

    public void insertRoots(WordRoot root){
        repository.insertRoots(root);
    }




}
