package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Word {
    private WordInfoBean word_info;
    /**
     * word_info : {"zh_source":"id","pronunciation_url":"http://hdqqnj.nr/wcsbg","meaning":"amet do cupidatat cillum ea","exam_grading":[false,false],"phonetic":"18165035413","id":80,"root":6,"example_sentences":[{"zh":"in est Lorem sed ad","en":"ad ea quis amet cupidatat"}],"variation":"eu mollit culpa","word":"occaecat in esse aliquip"}
     * id : 88
     * meaning : 基本意思
     * word : 单词
     * explanation : 解释
     * property : 词性
     */
    private int id;
    private String meaning;
    private String word;
    private String explanation;
    private String property;

    public Word(WordInfoBean word_info, int id) {
        this.word_info = word_info;
        this.id = id;
    }

    public WordInfoBean getWord_info() {
        return word_info;
    }

    public void setWord_info(WordInfoBean word_info) {
        this.word_info = word_info;
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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    /*
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

    public Word(String English,String Chinese){
        this.chinese = Chinese;
        english = English;
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
     */
}
