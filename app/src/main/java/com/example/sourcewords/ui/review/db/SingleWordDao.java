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

    @Query("SELECT * FROM single_word_table WHERE ID like :id")
    SingleWord getSingleById(int id);

    @Query("SELECT * FROM single_word_table WHERE 复习时间 like :time")
    List<SingleWord> getHaveLearnedWordsByTime(String time);

    @Query("SELECT * FROM single_word_table WHERE 单词 LIKE '%' || :word || '%' ")
    List<SingleWord> getLikelyWords(String word);

    @Query("SELECT * FROM SINGLE_WORD_TABLE WHERE 含义 LIKE '%' || :meaning || '%' ")
    List<SingleWord> getLikelyMeaning(String meaning);

    @Query("SELECT * FROM single_word_table ORDER BY ID")
    List<SingleWord> getAllWords();
}
