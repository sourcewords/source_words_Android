package com.example.sourcewords.ui.learn.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;

import java.util.List;

@Dao
public interface HistoryWordRootDao {
    @Insert
    void Insert(HistoryWordRoot...historyWordRoots);

    @Delete
    void Delete(HistoryWordRoot...historyWordRoots);

    @Query("DELETE FROM HistoryWordRoot")
    void Clear();

    @Query("SELECT * FROM HistoryWordRoot ORDER BY ID")
    List<HistoryWordRoot> getHistoryWRList();
}
