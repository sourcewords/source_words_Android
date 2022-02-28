package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryWord {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "word")
    String meaning;

    @ColumnInfo(name = "info")
    String root;

    public HistoryWord(String meaning, String root) {
        this.meaning = meaning;
        this.root = root;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
