package com.example.sourcewords.ui.review.model;

import android.os.AsyncTask;

import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.db.HistoryWordDao;
import com.example.sourcewords.ui.review.db.HistoryWordDatabase;

import java.util.List;

public class HistoryRepository {
    private HistoryWordDao mHistoryWordDao;

    public HistoryRepository(){
        HistoryWordDatabase hwdb = HistoryWordDatabase.getDatabase();
        mHistoryWordDao = hwdb.getHistoryWordDao();
    }

    public void Insert(HistoryWord historyWords, HistoryDBCallback HistoryDBCallback){new InsertAsync(mHistoryWordDao, HistoryDBCallback).execute(historyWords);}

    public void Delete(HistoryWord... historyWords){new DeleteAsync(mHistoryWordDao).execute(historyWords);}

    public void Clear(HistoryDBCallback HistoryDBCallback){new ClearAsync(mHistoryWordDao, HistoryDBCallback).execute();}

    public List<HistoryWord> getList(){return mHistoryWordDao.getHistoryWordList();}

    static class InsertAsync extends AsyncTask<HistoryWord, Void, Boolean>{
        HistoryWordDao mHistoryWordDao;
        HistoryDBCallback mFinishCallback;

        public InsertAsync(HistoryWordDao historyWordDao, HistoryDBCallback mFinishCallback) {
            mHistoryWordDao = historyWordDao;
            this.mFinishCallback = mFinishCallback;
        }

        @Override
        protected Boolean doInBackground(HistoryWord... historyWords) {
            mHistoryWordDao.Insert(historyWords);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isFinish) {
            mFinishCallback.checkIsFinish(isFinish);
            super.onPostExecute(isFinish);
        }
    }

    static class DeleteAsync extends AsyncTask<HistoryWord, Void, Void>{
        HistoryWordDao mHistoryWordDao;

        public DeleteAsync(HistoryWordDao historyWordDao) {
            mHistoryWordDao = historyWordDao;
        }

        @Override
        protected Void doInBackground(HistoryWord... historyWords) {
            mHistoryWordDao.Delete(historyWords);
            return null;
        }
    }

    static class ClearAsync extends AsyncTask<Void, Void, Boolean>{
        HistoryWordDao mHistoryWordDao;
        HistoryDBCallback mHistoryDBCallback;

        public ClearAsync(HistoryWordDao historyWordDao, HistoryDBCallback mHistoryDBCallback) {
            mHistoryWordDao = historyWordDao;
            this.mHistoryDBCallback = mHistoryDBCallback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            mHistoryWordDao.Clear();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mHistoryDBCallback.checkIsFinish(aBoolean);
            super.onPostExecute(aBoolean);
        }
    }
}
