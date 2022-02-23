package com.example.sourcewords.ui.review.dataBean;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    @Query("SELECT * FROM WORDROOT")
    LiveData<List<WordRoot>> getAllWordRoot();

    @Query("SELECT * FROM WORDROOT ORDER BY ID")
    List<WordRoot> getAllWordRootInSimple();

    @Query("SELECT * FROM WORDROOT WHERE id like :id")
    LiveData<WordRoot> getWordRoot(int id);

    @Query("SELECT * FROM WORDROOT WHERE 词根序号 =:id")
    WordRoot getWordRootById(int id);

    @Query("SELECT * FROM WORDROOT WHERE 词根 LIKE '%' || :searchMessage || '%'")
    List<WordRoot> getWordRootsSimilar(String searchMessage);



}
