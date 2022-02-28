package com.example.sourcewords.ui.learn.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.db.WordDao;
/**
 * 目前的想法是将所学的单词计划全部存到一个list里，然后随机/算法/日期之类的去进行删除每天已学的部分，
 * 并将这部分交给习部分，留下未完成的部分
 */
public class LearnedRepository {
    private static int Level;
    private final String NAME = "学习计划";
    private final String GRADE = "计划名称";
    private final WordDao wordDao;
    private final LearnWordDatabase db;
    private Context mContext;
    private static SharedPreferences sharedPreferences;

    private LearnedRepository(int level) {
        db = LearnWordDatabase.getDatabase();
        wordDao = db.getWordDao();
        if (getPlan() == -1){
            //没有学习计划
        }else if (getPlan() != level){
            //学习计划不一致
            updatePlan(level);//更新计划的标志
        }else{
            //学习计划一致，继续执行
        }
    }

    //TODO 计划初始化
    private void initPlan(){

    }

    private int getPlan(){
        SharedPreferences plan = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return plan.getInt(GRADE,-1);
    }

    public void updatePlan(int level){
        SharedPreferences plan = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = plan.edit();
        editor.putInt(GRADE,level);
        editor.apply();
    }

    public void insertWord(Word word){
        new InsertAsync(wordDao).execute(word);
    }

    public void deleteWord(Word word){
        new DeleteAsync(wordDao).execute(word);
    }

    static class InsertAsync extends AsyncTask<Word, Void, Void> {
        private WordDao dao;

        InsertAsync(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            dao.insertWord(words);
            return null;
        }
    }

    static class DeleteAsync extends AsyncTask<Word,Void,Void> {
        private WordDao dao;
        DeleteAsync(WordDao wordDao){
            this.dao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            dao.deleteWords(words);
            return null;
        }
    }
}
