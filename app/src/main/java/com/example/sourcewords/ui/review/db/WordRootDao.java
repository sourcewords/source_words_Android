package com.example.sourcewords.ui.review.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sourcewords.ui.review.dataBean.WordRoot;

import java.util.List;

@Dao
public interface WordRootDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoot(WordRoot... wordRoots);

    @Delete
    void deleteRoot(WordRoot... wordRoots);

    @Update
    void updateRoot(WordRoot... wordRoots);

    @Query("SELECT * FROM wordRoot_table")
    LiveData<List<WordRoot>> getAllWordRoot();

    @Query("SELECT * FROM wordRoot_table WHERE 词根序号 =:id")
    LiveData<WordRoot> getWordRootByID(int id);

    @Query("SELECT * FROM wordRoot_table WHERE 词根 LIKE '%' || :searchMessage || '%'")
    LiveData<List<WordRoot>> getWordRootsSimilar(String searchMessage);

    @Query("SELECT * FROM wordRoot_table WHERE 词根 LIKE :key || '%'")
    List<WordRoot> getLikelyWordRoot(String key);

    @Query("SELECT * FROM wordRoot_table WHERE 词根序号 =:id")
    WordRoot getRootById(int id);
}
