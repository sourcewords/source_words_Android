package com.example.sourcewords.ui.review.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.review.dataBean.HistoryWord;

@Database(entities = {HistoryWord.class}, version = 1, exportSchema = false)
public abstract class HistoryWordDatabase extends RoomDatabase {
    private static HistoryWordDatabase INSTANCE = getDatabase();

    public static HistoryWordDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (HistoryWordDatabase.class) {
                INSTANCE = Room.databaseBuilder(App.getAppContext(), HistoryWordDatabase.class, "HistoryWordDatabase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract HistoryWordDao getHistoryWordDao();
}
