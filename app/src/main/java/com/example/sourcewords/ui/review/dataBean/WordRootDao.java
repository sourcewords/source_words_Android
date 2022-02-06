package com.example.sourcewords.ui.review.dataBean;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordRootDao {
    @Insert
    void insertRoot(WordRoot... wordRoots);

    @Delete
    void deleteRoot(WordRoot... wordRoots);

    @Update
    void updateRoot(WordRoot... wordRoots);

    @Query("SELECT * FROM WORDROOT ORDER BY ID")
    LiveData<List<WordRoot>> getAllWordRoot();

    @Query("SELECT * FROM WORDROOT WHERE ID =:id")
    WordRoot getWordRootById(int id);

    @Query("SELECT * FROM WORDROOT WHERE 英文词根 LIKE '%' || :searchMessage || '%'")
    List<WordRoot> getWordRootsSimilar(String searchMessage);

}
