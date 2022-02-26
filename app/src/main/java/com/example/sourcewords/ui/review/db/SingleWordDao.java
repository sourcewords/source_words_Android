package com.example.sourcewords.ui.review.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;

import java.util.List;

@Dao
public interface SingleWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleWord(SingleWord...words);

    @Query("SELECT * FROM single_word_table")
    LiveData<List<SingleWord>> getAllWord();

}