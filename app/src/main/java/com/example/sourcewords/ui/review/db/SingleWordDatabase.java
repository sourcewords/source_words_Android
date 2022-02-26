package com.example.sourcewords.ui.review.db;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.db.WordDao;
import com.example.sourcewords.ui.review.db.WordRootDatabase;
import com.example.sourcewords.ui.review.model.WordDataSource;
import com.example.sourcewords.utils.Converters;

import java.io.IOException;
import java.util.List;

@Database(entities = {SingleWord.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SingleWordDatabase extends RoomDatabase {


    public abstract SingleWordDao getWordDao();

    private static final RoomDatabase.Callback roomDataBaseCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            try {
                new InitWbAsync(INSTANCE).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private static SingleWordDatabase INSTANCE = getDatabase();

    public static SingleWordDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (WordRootDatabase.class) {
                INSTANCE = Room.databaseBuilder(App.getAppContext(), SingleWordDatabase.class, "SingleWordDatabase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .addCallback(roomDataBaseCallBack)
                        .build();
            }

        }
        return INSTANCE;
    }

    static class InitWbAsync extends AsyncTask<Void, Void, Void> {
        private final SingleWordDao mDao;
        private List<SingleWord> words = WordDataSource.getSingleWords();

        public InitWbAsync(SingleWordDatabase instance) throws IOException {
            mDao = instance.getWordDao();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(SingleWord singleWord : words)
                mDao.insertSingleWord(singleWord);
            return null;
        }
    }
}