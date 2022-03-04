package com.example.sourcewords.ui.review.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sourcewords.ui.review.dataBean.HistoryWord;

import java.util.List;

@Dao
public interface HistoryWordDao {
    @Insert
    void Insert(HistoryWord...historyWords);

    @Delete
    void Delete(HistoryWord...historyWords);

    @Query("DELETE FROM HistoryWord")
    void Clear();

    @Query("SELECT * FROM HistoryWord ORDER BY ID")
    List<HistoryWord> getHistoryWordList();
}
