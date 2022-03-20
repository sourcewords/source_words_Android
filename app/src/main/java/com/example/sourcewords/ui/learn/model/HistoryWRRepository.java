package com.example.sourcewords.ui.learn.model;

import android.os.AsyncTask;

import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;
import com.example.sourcewords.ui.learn.db.HistoryWordRootDao;
import com.example.sourcewords.ui.learn.db.HistoryWordRootDatabase;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.db.WordRootDao;
import com.example.sourcewords.ui.review.db.WordRootDatabase;
import com.example.sourcewords.ui.review.model.HistoryDBCallback;

import java.util.List;

public class HistoryWRRepository {
    private final HistoryWordRootDao mHistoryWordRootDao;

    public HistoryWRRepository(){
        final HistoryWordRootDatabase historyWordRootDatabase = HistoryWordRootDatabase.getDatabase();
        mHistoryWordRootDao = historyWordRootDatabase.getHistoryWordRootDao();
    }

    public void Insert(HistoryWordRoot historyWordRoot, HistoryDBCallback mFinishCallback){new InsertAsync(mHistoryWordRootDao, mFinishCallback).execute(historyWordRoot);}

    public void Delete(HistoryWordRoot historyWordRoot, HistoryDBCallback mFinishCallback){new DeleteAsync(mHistoryWordRootDao, mFinishCallback).execute(historyWordRoot);}

    public void Clear(HistoryDBCallback mFinishCallback){new InsertAsync(mHistoryWordRootDao, mFinishCallback).execute();}

    public List<HistoryWordRoot> getList() {
        return mHistoryWordRootDao.getHistoryWRList();
    }

    static class InsertAsync extends AsyncTask<HistoryWordRoot, Void, Boolean>{
        HistoryWordRootDao mHistoryWordRootDao;
        HistoryDBCallback mFinishCallback;

        public InsertAsync(HistoryWordRootDao historyWordRootDao, HistoryDBCallback mFinishCallback) {
            mHistoryWordRootDao = historyWordRootDao;
            this.mFinishCallback = mFinishCallback;
        }

        @Override
        protected Boolean doInBackground(HistoryWordRoot... historyWordRoot) {
            mHistoryWordRootDao.Insert(historyWordRoot);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isFinish) {
            mFinishCallback.checkIsFinish(isFinish);
            super.onPostExecute(isFinish);
        }
    }

    static class DeleteAsync extends AsyncTask<HistoryWordRoot, Void, Boolean>{
        HistoryWordRootDao mHistoryWordRootDao;
        HistoryDBCallback mFinishCallback;

        public DeleteAsync(HistoryWordRootDao historyWordRootDao, HistoryDBCallback mFinishCallback) {
            mHistoryWordRootDao = historyWordRootDao;
            this.mFinishCallback = mFinishCallback;
        }

        @Override
        protected Boolean doInBackground(HistoryWordRoot... historyWordRoot) {
            mHistoryWordRootDao.Delete(historyWordRoot);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isFinish) {
            mFinishCallback.checkIsFinish(isFinish);
            super.onPostExecute(isFinish);
        }
    }

    static class ClearAsync extends AsyncTask<HistoryWordRoot, Void, Boolean>{
        HistoryWordRootDao mHistoryWordRootDao;
        HistoryDBCallback mFinishCallback;

        public ClearAsync(HistoryWordRootDao historyWordRootDao, HistoryDBCallback mFinishCallback) {
            mHistoryWordRootDao = historyWordRootDao;
            this.mFinishCallback = mFinishCallback;
        }

        @Override
        protected Boolean doInBackground(HistoryWordRoot... historyWordRoot) {
            mHistoryWordRootDao.Clear();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isFinish) {
            mFinishCallback.checkIsFinish(isFinish);
            super.onPostExecute(isFinish);
        }
    }
}
