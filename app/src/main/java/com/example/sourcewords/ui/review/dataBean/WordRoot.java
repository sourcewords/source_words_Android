package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.sourcewords.utils.Converters;

import java.util.List;

@Entity
public class WordRoot {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "英文词根")
    private String englishRoot;
    @ColumnInfo(name = "词根意思")
    private String meaningOfRoot;
    @ColumnInfo(name = "单词表")
    private List<Word> list;
    @ColumnInfo(name = "视频网址")
    private String path;

    public WordRoot(int id, String englishRoot, String meaningOfRoot, List<Word> list) {
        this.id = id;
        this.englishRoot = englishRoot;
        this.meaningOfRoot = meaningOfRoot;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishRoot() {
        return englishRoot;
    }

    public void setEnglishRoot(String englishRoot) {
        this.englishRoot = englishRoot;
    }

    public String getMeaningOfRoot() {
        return meaningOfRoot;
    }

    public void setMeaningOfRoot(String meaningOfRoot) {
        this.meaningOfRoot = meaningOfRoot;
    }

    public List<Word> getList() {
        return list;
    }

    public void setList(List<Word> list) {
        this.list = list;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
