package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryWord {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "word")
    String word;

    @ColumnInfo(name = "info")
    String info;

    public HistoryWord(int id, String word, String info) {
        this.id = id;
        this.word = word;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
