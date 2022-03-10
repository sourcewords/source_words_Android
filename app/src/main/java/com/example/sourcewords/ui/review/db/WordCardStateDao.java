package com.example.sourcewords.ui.review.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.dataBean.WordCardState;

@Dao
public interface WordCardStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(WordCardState...wordCardStates);

    @Query("DELETE FROM word_card_state")
    void Delete();

    @Query("SELECT * FROM word_card_state WHERE date like :date")
    WordCardState Search(long date);
}
