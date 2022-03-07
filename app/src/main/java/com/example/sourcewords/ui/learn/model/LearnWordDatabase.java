package com.example.sourcewords.ui.learn.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.db.WordDao;
import com.example.sourcewords.utils.Converters;

//TODO 记录所学的计划以及剩余的单词
@Database(entities = {Word.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class LearnWordDatabase extends RoomDatabase {
    private static LearnWordDatabase INSTANCE;

    public static LearnWordDatabase getDatabase() {
        if (INSTANCE == null) {

            synchronized (LearnWordDatabase.class) {
                INSTANCE = Room.databaseBuilder(App.getAppContext(), LearnWordDatabase.class, "WordRootDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
            }

        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();
}
