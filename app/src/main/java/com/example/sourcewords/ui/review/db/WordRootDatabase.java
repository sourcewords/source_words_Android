package com.example.sourcewords.ui.review.db;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.review.model.WordDataSource;
import com.example.sourcewords.utils.Converters;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.io.IOException;
import java.util.List;

@Database(entities = {WordRoot.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WordRootDatabase extends RoomDatabase {

    public abstract WordRootDao getWordDao();

    private static final RoomDatabase.Callback roomDataBaseCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            try {
                Log.d("initData","");
                new InitDbAsync(INSTANCE).execute();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    };
    private static WordRootDatabase INSTANCE = getDatabase();

    public static WordRootDatabase getDatabase() {
        if (INSTANCE == null) {
            //这里加互斥锁，是为了防止多线程重复创建数据库，保证单例在多线程操作的安全性
            synchronized (WordRootDatabase.class) {
                // 这里的上下文我已经在App里写明了，不需要重复传参
                INSTANCE = Room.databaseBuilder(App.getAppContext(), WordRootDatabase.class, "WordRootDatabase")
                        //性能出发，为防止查询时ui卡顿，不建议在主线程查询，采用异步查询
                        //.allowMainThreadQueries()
                        // 为之后数据库添加字段删除字段的和平过渡
                        .fallbackToDestructiveMigration()
                        .addCallback(roomDataBaseCallBack)
                        .build();
            }

        }
        return INSTANCE;
    }

    private static class InitDbAsync extends AsyncTask<Void,Void,Void> {
        private final WordRootDao mDao;
        private List<WordRoot> list =  WordDataSource.getRoots();

        public InitDbAsync(WordRootDatabase instance) throws IOException {
            mDao = instance.getWordDao();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Log.d("init","");
            for(WordRoot wordRoot : list) {
                mDao.insertRoot(wordRoot);
                Log.d("initDataq","" + wordRoot.getId());
            }
            return null;
        }
    }

}
