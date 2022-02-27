package com.example.sourcewords.ui.review.model;


import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordDao;
import com.example.sourcewords.ui.review.db.WordDatabase;
import com.example.sourcewords.ui.review.db.WordRootDao;
import com.example.sourcewords.ui.review.db.WordRootDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


@RequiresApi(api = Build.VERSION_CODES.N)
public class WordRepository {
    private WordRootDao dao;
    private LiveData<WordRoot> wordRootList;

    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository() {
        WordRootDatabase db = WordRootDatabase.getDatabase();
        WordDatabase wordDatabase = WordDatabase.getDatabase();
        dao = db.getWordDao();
        wordDao = wordDatabase.getWordDao();
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

    public WordRoot getWordRootByID(int id){return dao.getWordRootById(id);}

    public void insert(Word...words) {
        new InsertWords(wordDao).execute(words);
    }

    public Word search(int id) {
        new Search(wordDao).execute(id);
        return wordDao.getWord(id);
    }

    public WordRoot getWordRootTest(int id) {
        List<Word> list = new ArrayList<>();
        for(int i = 0; i<4; i++) {
            List<String> l = new ArrayList<>();
            //WordInfoBean wordInfo = new WordInfoBean();
            //Word temp = new Word(wordInfo, i, "Chinese" + i, "sncjs" + i, "lalalal","aa");
            //list.add(temp);
        }
        return new WordRoot(2,"chi","xxx",99 ,"aa","bb",list);
    }

    public WordRoot getWordRootTest2(int id) {
        List<Word> list = new ArrayList<>();
        List<Boolean> listB = new ArrayList<>();
        for(int i=0; i<12; i++){
            listB.add(false);
        }

        List<WordInfoBean.ExampleSentencesBean> listE1 = new ArrayList<>();
        listE1.add(new WordInfoBean.ExampleSentencesBean("我要讲一个淫猥的故事。","I have an impure tale to tell ."));
        listE1.add(new WordInfoBean.ExampleSentencesBean("准备投入这淫猥的故事。","Prepare yourself for an impure tale ."));
        WordInfoBean wordInfo1 = new WordInfoBean("impure 不纯的，有杂质的im-,不，非，pure,纯的。即不纯的，有杂质的。",
                "https://dict.youdao.com/dictvoice?type=0\u0026audio=impure",
                "adj. 掺杂的； 掺假的；脏的；不纯洁的",
                "[ɪmˈpjʊə]",
                83, 41,"","impure",listB,listE1
                );
        Word word1 = new Word(wordInfo1,84);
        list.add(word1);

        List<WordInfoBean.ExampleSentencesBean> listE2 = new ArrayList<>();
        listE2.add(new WordInfoBean.ExampleSentencesBean("据最近发表的一份研究报告，母亲的年龄是一个因素，父亲的年龄同样是一个因素。","Maternal age is a factor and , according to recent research , so is paternal age ."));
        listE2.add(new WordInfoBean.ExampleSentencesBean("政府还应当通过立法来让雇主提供孩子哺乳期时的母亲和父亲的假期，或提供儿童抚养补助。","Governments should also legislate to get employers to offer both maternal and paternal leave , and provide or subsidise child care ."));
        WordInfoBean wordInfo2 = new WordInfoBean("paternal 父亲的，父爱的来自拉丁语pater,父亲，词源同father.",
                "https://dict.youdao.com/dictvoice?type=0\u0026audio=paternal",
                "adj. 父亲的；父亲（般）的；父系的；父方的",
                "[pəˈtɜ:nl]",
                84, 41,"","paternal",listB,listE2
        );
        Word word2 = new Word(wordInfo2,82);
        list.add(word2);

        return new WordRoot(1,"w","w",1,"w","w",list);
    }

    PriorityQueue<InnerWord> priorityQueue = new PriorityQueue<InnerWord>(new Comparator<InnerWord>() {
        @Override
        public int compare(InnerWord innerWord1, InnerWord innerWord2) {
            return innerWord1.priority - innerWord2.priority;
        }
    });

    List<Word> currentWords = new ArrayList<>();

    /**
     * 我们采用这种复习的策略，在刷新单词状态时因为单词有在今天不要复习，可以直接在数据库修改nexttime，但是对于今
     * 天要复习的单词，我们将现在日期的所有要复习的单词按照下次要复习分钟数做成一个优先队列，我们在这里写一个内部
     * 类，根据word和下次复习的分钟数，这样的话，我们每次往viewmodel传一个队列，当viewmodel的队列用完了以后，
     * 会再次向这里请求队列，由于在复习的时候，会调用上面的单词状态刷新方法，所以每一轮会有单词的复习日期变化，存储
     * 进数据库，最后这个队列会变成一个空队列，然后，viewmodel那里请求单词也请求不到，我们在这个时候把数据同步到云端
     */


    //TODO:设置当日学到的词根，应该从learn那里调用
//    public WordRoot setWordRootToday(String time) {
//
//    }

    //TODO:拿到学完当日词根而要学的新的单词
    public List<Word> getNewWords() {
        return getWordRootTest2(1).getWordlist();
    }

    // TODO:通过日期拿到其他今天要复习的单词
    // 这里一开始所有的单词进入队列都是一样的
    public List<Word> getLearnedWords(String time) {

        return getWordRootTest2(1).getWordlist();
    }

    // TODO:刷新单词的状态
    // 通过detail种不同的选择，我们对于不同的单词做不同的处理，我们按照一轮一轮的复习策略
    // 也就是说，对于state可以是10min, 20min,5min, 还有几天这样，和wordinfo的status区分开
    // 然后根据这个队列，我们把一个已经有优先级的queue化为list传给model
//    public void freshWord(int wordID, int state) {
//
//    }

    // TODO:将优先队列化为list
    // 我会在viewmodel里做判空处理，如果为空，我会把修改试图，且今日的所有单词复习完毕
    public List<Word> getCurrentWords() {
        // 这里直接出队列就可以
        return new ArrayList<>();
    }

    static class InsertWords extends AsyncTask<Word,Void,Void> {
        WordDao dao;
        InsertWords(WordDao wordDao){dao = wordDao;}

        @Override
        protected Void doInBackground(Word... words) {
            dao.insertWord(words);
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

    class InnerWord {
        // 单词本身
        Word word;
        // 优先级
        // 0
        // 1
        // 2
        // 3按照那几种分钟的长短，越短越优先级越高
        int priority;

    }
}
