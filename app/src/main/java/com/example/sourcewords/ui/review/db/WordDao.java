package com.example.sourcewords.ui.review.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sourcewords.ui.review.dataBean.Word;

import java.util.List;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Update
    void updateRoot(Word... words);

    @Query("DELETE FROM word_table")
    void clearAll();

    @Query("SELECT * FROM word_table")
    LiveData<List<Word>> getAllWord();

    @Query("SELECT * FROM word_table WHERE ID like :id")
    Word getWord(int id);

    @Query("SELECT * FROM word_table WHERE 词根ID like :id")
    LiveData<List<Word>> getWordsByRootID(int id);

}
