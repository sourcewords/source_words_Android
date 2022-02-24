package com.example.sourcewords.ui.review.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.utils.Converters;

@Database(entities = {Word.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;


    public static WordDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (WordRootDatabase.class) {
                INSTANCE = Room.databaseBuilder(App.getAppContext(), WordDatabase.class, "WordDatabase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }

        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();
}
