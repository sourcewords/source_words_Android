package com.example.sourcewords.ui.learn.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryWordRoot {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "储存id")
    int saveId;

    @ColumnInfo(name = "词根")
    String wordRoot;

    @ColumnInfo(name = "中文意思")
    String meaning;

    public HistoryWordRoot(String wordRoot, int saveId, String meaning) {
        this.wordRoot = wordRoot;
        this.saveId = saveId;
        this.meaning = meaning;
    }

    public int getSaveId() {
        return saveId;
    }

    public void setSaveId(int saveId) {
        this.saveId = saveId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordRoot() {
        return wordRoot;
    }

    public void setWordRoot(String wordRoot) {
        this.wordRoot = wordRoot;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
