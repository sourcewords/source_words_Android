package com.example.sourcewords.ui.review.model;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.SingleWordDao;
import com.example.sourcewords.ui.review.db.SingleWordDatabase;
import com.example.sourcewords.ui.review.db.WordDao;
import com.example.sourcewords.ui.review.db.WordDatabase;
import com.example.sourcewords.ui.review.db.WordRootDao;
import com.example.sourcewords.ui.review.db.WordRootDatabase;
import com.example.sourcewords.ui.review.view.reviewUtils.WordSample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.N)
public class WordRepository {
    private WordRootDao dao;

    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    private SingleWordDao singleWordDao;
    private LiveData<List<SingleWord>> allSingleWord;

    public WordRepository() {
        WordRootDatabase db = WordRootDatabase.getDatabase();
        WordDatabase wordDatabase = WordDatabase.getDatabase();
        SingleWordDatabase singleWordDatabase = SingleWordDatabase.getDatabase();

        dao = db.getWordDao();
        /*
        List<WordRoot> list = dao.getAllWordRootInSimple();
        String time = DateUtils.getTime();
        for(WordRoot wordRoot : list){
            for(Word word : wordRoot.getWordlist()){
                if(word.getWord_info().getStatus() == WordInfoBean.WORD_NEW) newWordsToday.add(word);
                else if(word.getWord_info().getStatus() == WordInfoBean.WORD_TODAY_REVIEW_AGAIN) reviewAgainToday.add(word);
                else if(word.getWord_info().getStatus() == WordInfoBean.WORD_PAST_REVIEWED) {
                    if(time.substring(0,9).compareTo(word.getWord_info().getNextTime().substring(0,9)) <= 0){
                        tobeReview.add(word);
                    }
                }
            }
        }
        */
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
        return dao.getRootById(id);
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

    public void getLikelyWords(String word, SingleWordDBCallBack singleWordDBCallBack){
        new SearchWord(singleWordDao, singleWordDBCallBack).execute(word);
    }

    public void getLikelyMeaning(String meaning, SingleWordDBCallBack singleWordDBCallBack){
        new SearchMeaning(singleWordDao, singleWordDBCallBack).execute(meaning);
    }

    public List<SingleWord> getAllWord(){
        return singleWordDao.getAllWords();
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

    public void loadAllWordsToDataBase(HashMap<Integer, WordSample> hashMap) {
        if(hashMap == null) return;
        for(int key : hashMap.keySet()) {
            Word word = hashMap.get(key).getWord();
            int status = hashMap.get(key).getStatus();
            String nextTime = hashMap.get(key).getTime();

            word.getWord_info().setNextTime(nextTime);
            word.getWord_info().setStatus(status);
            wordDao.insertWord(word);

            SingleWord singleWord = singleWordDao.getSingleById(word.getId());
            singleWord.setNextTime(nextTime);
            singleWord.setStatus(status);
            singleWordDao.insertSingleWord(singleWord);
        }
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

    static class SearchWord extends AsyncTask<String, Void, List<SingleWord>> {
        SingleWordDao mSingleWordDao;
        SingleWordDBCallBack mDBCallback;

        public SearchWord(SingleWordDao singleWordDao, SingleWordDBCallBack DBCallback) {
            mSingleWordDao = singleWordDao;
            mDBCallback = DBCallback;
        }

        @Override
        protected List<SingleWord> doInBackground(String... keyWords) {
            return mSingleWordDao.getLikelyWords(keyWords[0]);
        }

        @Override
        protected void onPostExecute(List<SingleWord> singleWords) {
            mDBCallback.getList(singleWords);
            super.onPostExecute(singleWords);
        }
    }

    static class SearchMeaning extends AsyncTask<String, Void, List<SingleWord>> {
        SingleWordDao mSingleWordDao;
        SingleWordDBCallBack mDBCallback;

        public SearchMeaning(SingleWordDao singleWordDao, SingleWordDBCallBack DBCallback) {
            mSingleWordDao = singleWordDao;
            mDBCallback = DBCallback;
        }

        @Override
        protected List<SingleWord> doInBackground(String... keyWords) {
            return mSingleWordDao.getLikelyMeaning(keyWords[0]);
        }

        @Override
        protected void onPostExecute(List<SingleWord> singleWords) {
            mDBCallback.getList(singleWords);
            super.onPostExecute(singleWords);
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
            return mWordRootDao.getRootById(id);
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
