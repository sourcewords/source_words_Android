package com.example.sourcewords.ui.review.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sourcewords.utils.Converters;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootDao;

@Database(entities = {WordRoot.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;
    static WordDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "WordDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordRootDao getWordDao();
}
