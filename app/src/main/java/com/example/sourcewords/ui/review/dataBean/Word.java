package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "中文单词")
    private String chinese;
    @ColumnInfo(name = "英文意思")
    private String english;
    @ColumnInfo(name = "音标")
    private String soundMark;
    @ColumnInfo(name = "结构分析")
    private String structure;
    @ColumnInfo(name = "例句")
    private List<String> examples;

    public Word() {
    }

    public Word(int id, String chinese, String english, String soundMark, String structure, List<String> examples) {
        this.id = id;
        this.chinese = chinese;
        this.english = english;
        this.soundMark = soundMark;
        this.structure = structure;
        this.examples = examples;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getSoundMark() {
        return soundMark;
    }

    public void setSoundMark(String soundMark) {
        this.soundMark = soundMark;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }
}
