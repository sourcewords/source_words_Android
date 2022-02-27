package com.example.sourcewords.ui.review.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sourcewords.App;
import com.example.sourcewords.utils.Converters;
import com.example.sourcewords.ui.review.dataBean.WordRoot;

@Database(entities = {WordRoot.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WordRootDatabase extends RoomDatabase {
    private static WordRootDatabase INSTANCE;


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
                        .build();
            }

        }
        return INSTANCE;
    }

    public abstract WordRootDao getWordDao();
}
