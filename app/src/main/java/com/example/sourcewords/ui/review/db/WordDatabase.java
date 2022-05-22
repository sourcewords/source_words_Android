package com.example.sourcewords.ui.review.db;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.main.MainFragment;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordDataSource;
import com.example.sourcewords.utils.Converters;
import java.io.IOException;
import java.util.List;

@Database(entities = {Word.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WordDatabase extends RoomDatabase {

    public abstract WordDao getWordDao();

    private static final RoomDatabase.Callback roomDataBaseCallBack = new Callback() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            try {
                new WordDatabase.InitWbAsync(INSTANCE).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private static WordDatabase INSTANCE = getDatabase();

    public static WordDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (WordRootDatabase.class) {
                INSTANCE = Room.databaseBuilder(App.getAppContext(), WordDatabase.class, "WordDatabase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .addCallback(roomDataBaseCallBack)
                        .build();
            }

        }
        return INSTANCE;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static class InitWbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao mDao;
        private List<WordRoot> roots = WordDataSource.getRoots();

        public InitWbAsync(WordDatabase instance) throws IOException {
            mDao = instance.getWordDao();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (WordRoot root : roots) {
                List<Word> list = root.getWordlist();
                for (Word word : list) {
                    mDao.insertWord(word);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            MainFragment.num++;
            MainFragment.remove.postValue(MainFragment.num);
            super.onPostExecute(unused);

        }
    }
}
