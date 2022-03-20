package com.example.sourcewords.ui.learn.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;
import com.example.sourcewords.ui.review.db.HistoryWordDao;
import com.example.sourcewords.ui.review.db.HistoryWordDatabase;

@Database(entities = HistoryWordRoot.class, version = 1, exportSchema = false)
public abstract class HistoryWordRootDatabase extends RoomDatabase{
    private static HistoryWordRootDatabase INSTANCE = getDatabase();

    public static HistoryWordRootDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (HistoryWordRootDatabase.class) {
                INSTANCE = Room.databaseBuilder(App.getAppContext(), HistoryWordRootDatabase.class, "HistoryWordDatabase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract HistoryWordRootDao getHistoryWordRootDao();
}
