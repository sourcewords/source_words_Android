package com.example.sourcewords.ui.review.model;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootDao;
import com.example.sourcewords.ui.review.db.WordDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WordRepository {
    private WordRootDao dao;
    private LiveData<WordRoot> wordRootList;

    public WordRepository() {
        WordDatabase db = WordDatabase.getDatabase();
        dao = db.getWordDao();
    }

    public void Insert(WordRoot...wordRoots){
        new Insert(dao).execute(wordRoots);
    }

    public void Delete(WordRoot...wordRoots){
        new Delete(dao).execute(wordRoots);
    }

    public void Update(WordRoot...wordRoots){
        new Update(dao).execute(wordRoots);
    }

    public LiveData<WordRoot> getWordRoot(int id) {
        return dao.getWordRoot(id);
    }

    public LiveData<List<WordRoot>> getAllWordRoot(){return  dao.getAllWordRoot();}

    public WordRoot getWordRootTest(int id) {
        List<Word> list = new ArrayList<>();
        for(int i = 0; i<4; i++) {
            List<String> l = new ArrayList<>();
            WordInfoBean wordInfo = new WordInfoBean();
            Word temp = new Word(wordInfo, i, "Chinese" + i, "sncjs" + i, "lalalal","aa");
            list.add(temp);
        }
        return new WordRoot(2,"chi","xxx",99 ,"aa","bb",list);
    }

    public WordRoot getWordRootTest2(int id) {
        return null;
    }

    static class Insert extends AsyncTask<WordRoot,Void,Void>{
        WordRootDao wordDao;
        Insert(WordRootDao wordDao){
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordRoot... wordRoots) {
            wordDao.insertRoot(wordRoots);
            return null;
        }
    }

    static class Delete extends AsyncTask<WordRoot,Void,Void>{
        WordRootDao wordDao;
        Delete(WordRootDao wordDao){
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordRoot... wordRoots) {
            wordDao.deleteRoot(wordRoots);
            return null;
        }
    }

    static class Update extends AsyncTask<WordRoot,Void,Void>{
        WordRootDao wordDao;
        Update(WordRootDao wordDao){
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordRoot... wordRoots) {
            wordDao.updateRoot(wordRoots);
            return null;
        }
    }
}
