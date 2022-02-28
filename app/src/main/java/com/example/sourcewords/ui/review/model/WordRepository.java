package com.example.sourcewords.ui.review.model;


import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.SingleWordDao;
import com.example.sourcewords.ui.review.db.SingleWordDatabase;
import com.example.sourcewords.ui.review.db.WordDao;
import com.example.sourcewords.ui.review.db.WordDatabase;
import com.example.sourcewords.ui.review.db.WordRootDao;
import com.example.sourcewords.ui.review.db.WordRootDatabase;

import org.bouncycastle.asn1.ASN1Boolean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class WordRepository {
    private WordRootDao dao;
    private LiveData<WordRoot> wordRootList;

    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    private SingleWordDao singleWordDao;
    private LiveData<List<SingleWord>> allSingleWord;

    public WordRepository() {
        WordRootDatabase db = WordRootDatabase.getDatabase();
        WordDatabase wordDatabase = WordDatabase.getDatabase();
        SingleWordDatabase singleWordDatabase = SingleWordDatabase.getDatabase();

        dao = db.getWordDao();
        wordDao = wordDatabase.getWordDao();
        singleWordDao = singleWordDatabase.getWordDao();

        allWords = wordDao.getAllWord();
        allSingleWord = singleWordDao.getAllWord();
    }

    public LiveData<List<SingleWord>> getAllSingleWord() {
        return allSingleWord;
    }

    public SingleWord getSingleWordById(int id) {
        return singleWordDao.getSingleById(id);
    }

    public void insert(SingleWord...singleWords) {
        singleWordDao.insertSingleWord(singleWords);
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


    public LiveData<List<WordRoot>> getAllWordRoot(){return dao.getAllWordRoot();}

    public LiveData<List<Word>> getAllWords(){
        return wordDao.getAllWord();
    }

    public WordRoot getWordRootByID(int id){
        Log.d("root",""+ id);
        return dao.getWordRootByID(id);
    }

    public void insert(Word...words) {
        new InsertWords(wordDao).execute(words);
    }

    public Word search(int id) {
        return wordDao.getWord(id);
    }


    public List<Word> getNewWords(int woodRootId) {
        if(woodRootId == 0) return new ArrayList<>();

        WordRoot wordRoot = getWordRootByID(woodRootId);
        return wordRoot.getWordlist();
    }


    public List<Word> getLearnedWords(String time) {
        List<SingleWord> list = singleWordDao.getHaveLearnedWordsByTime(time);

        List<Word> words = new ArrayList<>();
        for(SingleWord singleWord : list) {
            Word word = wordDao.getWord(singleWord.getId());
            Log.d("initData",singleWord.toString());
            word.getWord_info().setStatus(singleWord.getStatus());
            word.getWord_info().setNextTime(singleWord.getNextTime());
            words.add(word);
        }
        return words;
    }

    static class InsertWords extends AsyncTask<Word,Void,Void> {
        WordDao dao;
        InsertWords(WordDao wordDao){dao = wordDao;}

        @Override
        protected Void doInBackground(Word... words) {
            dao.insertWord(words[0]);
            return null;
        }
    }

    static class Search extends AsyncTask<Integer, Void, Word> {
        WordDao dao;
        Search(WordDao wordDao){dao = wordDao;}

        @Override
        protected Word doInBackground(Integer... integers) {
            int id = integers[0];
            return dao.getWord(id);
        }
    }

    static class Insert extends AsyncTask<WordRoot,Void,Void>{
        WordRootDao mWordRootDao;
        Insert(WordRootDao wordRootDao){mWordRootDao = wordRootDao;}
        @Override
        protected Void doInBackground(WordRoot... wordRoots) {
            mWordRootDao.insertRoot(wordRoots);
            return null;
        }
    }

    static class Delete extends AsyncTask<WordRoot,Void,Void>{
        WordRootDao mWordRootDao;
        Delete(WordRootDao wordRootDao){mWordRootDao = wordRootDao;}
        @Override
        protected Void doInBackground(WordRoot... wordRoots) {
            mWordRootDao.deleteRoot(wordRoots);
            return null;
        }
    }

    static class Update extends AsyncTask<WordRoot,Void,Void>{
        WordRootDao mWordRootDao;
        Update(WordRootDao wordRootDao){mWordRootDao = wordRootDao;}
        @Override
        protected Void doInBackground(WordRoot... wordRoots) {
            mWordRootDao.updateRoot(wordRoots);
            return null;
        }
    }

    static class GetWordRootByID extends AsyncTask<Integer, Void, WordRoot>{
        WordRootDao mWordRootDao;
        GetWordRootByID(WordRootDao wordRootDao) {
            this.mWordRootDao = wordRootDao;
        }

        @Override
        protected WordRoot doInBackground(Integer... integers) {
            int id = integers[0];
            return mWordRootDao.getWordRootByID(id);
        }
    }

    static class GetAllWord extends AsyncTask<Void, Void, LiveData<List<Word>>>{
        WordDao wordDao;
        GetAllWord(WordDao wordDao) {
            this.wordDao = wordDao;
        }
        @Override
        protected LiveData<List<Word>> doInBackground(Void... voids) {
            return wordDao.getAllWord();
        }
    }

}
