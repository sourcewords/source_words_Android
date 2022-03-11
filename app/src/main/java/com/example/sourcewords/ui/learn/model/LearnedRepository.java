package com.example.sourcewords.ui.learn.model;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.sourcewords.commonUtils.SPUtils;
import com.example.sourcewords.ui.learn.model.Internet.Test;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordDao;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 目前的想法是将所学的单词计划全部存到一个list里，然后随机/算法/日期之类的去进行删除每天已学的部分，
 * 并将这部分交给习部分，留下未完成的部分
 */
public class LearnedRepository {
    private final WordDao dao;
    private static final String KEY_PLAN = "key_plan";
    private final String Authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDQ0Nzk5NDksImlhdCI6MTY0NDM5MzU0OSwidWlkIjoxN30.3fA571_ktll7xL1aBSwEiAyoXc0QvmwdXt3XlyCw1VQ";

    public LearnedRepository() {//level 用户当前的计划
        LearnWordDatabase db = LearnWordDatabase.getDatabase();
        dao = db.getWordDao();
    }

    public void clearAll() {
        dao.clearAll();
    }

    public LiveData<List<Word>> getWordsByRootID(int root_id){
        return dao.getWordsByRootID(root_id);
    }

    public boolean isSamePlan(int level) {
        return level == getPlan();
    }


    private int getPlan() {
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_PLAN);
        return sp.getInt(KEY_PLAN, -1);
    }

    public void updatePlan(int level) {
        SPUtils sp = SPUtils.getInstance(SPUtils.SP_LEARN_PLAN);
        sp.put(KEY_PLAN, level);
    }

    private void insertWordToPlan(Word word) {
        Log.d("LearnInit",String.valueOf(word.getId()));
        new InsertAsync(dao).execute(word);
    }

    public void deleteWordFromPlan(Word word) {
        new DeleteAsync(dao).execute(word);
    }

    static class InsertAsync extends AsyncTask<Word, Void, Void> {
        private final WordDao dao;

        InsertAsync(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            dao.insertWord(words);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<Word, Void, Void> {
        private final WordDao dao;

        DeleteAsync(WordDao wordDao) {
            this.dao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            dao.deleteWords(words);
            return null;
        }
    }


}
