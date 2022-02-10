package com.example.sourcewords.ui.review.model;


import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootDao;
import com.example.sourcewords.ui.review.db.WordDatabase;

import java.util.ArrayList;
import java.util.List;


public class WordRepository {
    private WordRootDao dao;
    private LiveData<WordRoot> wordRootList ;

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

    public WordRoot getWordRootByID(int id){return dao.getWordRootById(id);}

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
                84, 41,"","impure",listB,listE1
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
                85, 41,"","paternal",listB,listE2
        );
        Word word2 = new Word(wordInfo1,84);
        list.add(word2);

        return new WordRoot(1,"w","w",1,"w","w",list);
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
