package com.example.sourcewords.ui.review.model;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordCardState;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.SingleWordDao;
import com.example.sourcewords.ui.review.db.SingleWordDatabase;
import com.example.sourcewords.ui.review.db.WordCardStateDao;
import com.example.sourcewords.ui.review.db.WordCardStateDatabase;
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
    private final WordRootDao dao;
    private final WordCardStateDao wordCardStateDao;
    private final WordDao wordDao;
    private LiveData<List<Word>> allWords;

    private SingleWordDao singleWordDao;
    private LiveData<List<SingleWord>> allSingleWord;

    public static void initialize() {
        WordRootDatabase.getDatabase();
        WordDatabase.getDatabase();
        SingleWordDatabase.getDatabase();
    }

    public WordRepository() {
        WordRootDatabase db = WordRootDatabase.getDatabase();
        WordDatabase wordDatabase = WordDatabase.getDatabase();
        SingleWordDatabase singleWordDatabase = SingleWordDatabase.getDatabase();
        WordCardStateDatabase wordCardStateDatabase = WordCardStateDatabase.getDatabase();

        dao = db.getWordDao();
        wordDao = wordDatabase.getWordDao();
        singleWordDao = singleWordDatabase.getWordDao();
        wordCardStateDao = wordCardStateDatabase.geWordCardStateDao();

        allWords = wordDao.getAllWord();
        allSingleWord = singleWordDao.getAllWord();
    }

    public void deleteWordCardState() {
        wordCardStateDao.Delete();
    }

    public void insertWordCardState(WordCardState...wordCardStates) {
        wordCardStateDao.Insert(wordCardStates);
    }

    public WordCardState getWordCardState(long date) {
        return wordCardStateDao.Search(date);
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


    public List<Word> getNewWords(int wordRootId) {
        if(wordRootId == 0) return new ArrayList<>();
        WordRoot wordRoot = getWordRootByID(wordRootId);
        Log.d("preferencesWord", wordRoot.getWordlist().size() + "");

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


}
