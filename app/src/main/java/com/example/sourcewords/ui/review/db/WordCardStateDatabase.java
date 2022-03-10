package com.example.sourcewords.ui.review.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.review.dataBean.WordCardState;
import com.example.sourcewords.utils.Converters;


@Database(entities = {WordCardState.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WordCardStateDatabase extends RoomDatabase {
    private static WordCardStateDatabase INSTANCE = getDatabase();

    public static WordCardStateDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (WordCardStateDatabase.class) {
                INSTANCE = Room.databaseBuilder(App.getAppContext(), WordCardStateDatabase.class, "WordCardStateDatabase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract WordCardStateDao geWordCardStateDao();
}
