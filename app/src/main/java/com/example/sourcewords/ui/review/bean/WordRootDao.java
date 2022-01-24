package com.example.sourcewords.ui.review.bean;

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
    void insertRoot(WordRoot...wordRoots);
    @Delete
    void deleteRoot(WordRoot...wordRoots);
    @Update
    void updateRoot(WordRoot...wordRoots);
    @Query("SELECT*FROM WORDROOT ORDER BY ID")
    LiveData<List<WordRoot>> getAllWordRoot();
}
