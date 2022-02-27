package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "single_word_table")
public class SingleWord {
    /**
     * id : 3
     * word : dent
     * root : 1
     * phonetic : [dent]
     * pronunciation_url : https://dict.youdao.com/dictvoice?type=0&audio=dent
     * meaning : n. 凹痕，凹部；齿；减少，削弱；[名]登特vt. 使产生凹痕；削弱；打击，破坏；产生不好的影响vi. 出现凹痕；产生凹陷；凹进去；削减
     * zh_source : dent 凹痕来自dint的拼写变体，敲打，拟声词。
     * variation : 复数： dents第三人称单数： dents过去式： dented过去分词： dented现在分词： denting易混淆的单词： DentDENT
     * example_sentences : [{"en":"That would dent demand for both coal and electricity .","zh":"这会削弱对煤和电的需求。"},{"en":"Hurled accusations can make an especially huge dent in your relationship .","zh":"愤怒的指控能够极大地削弱你的人际关系。"}]
     * exam_grading : [false,false,false,false,false,false,false,false,false,false,false,false]
     */
    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "单词")
    private String word;

    @ColumnInfo(name = "词根id")
    private int root;

    @ColumnInfo(name = "音标")
    private String phonetic;

    @ColumnInfo(name = "发音")
    private String pronunciation_url;

    @ColumnInfo(name = "含义")
    private String meaning;

    @ColumnInfo(name = "词源")
    private String zh_source;

    @ColumnInfo(name = "单词变形")
    private String variation;

    @ColumnInfo(name = "复习时间")
    private String nextTime;

    @ColumnInfo(name = "复习状态")
    private int status;
    /**
     * en : That would dent demand for both coal and electricity .
     * zh : 这会削弱对煤和电的需求。
     */

    @ColumnInfo(name = "例句")
    private List<ExampleSentencesBean> example_sentences;
    @ColumnInfo(name = "考试分级")
    private List<Boolean> exam_grading;

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getPronunciation_url() {
        return pronunciation_url;
    }

    public void setPronunciation_url(String pronunciation_url) {
        this.pronunciation_url = pronunciation_url;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getZh_source() {
        return zh_source;
    }

    public void setZh_source(String zh_source) {
        this.zh_source = zh_source;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public List<ExampleSentencesBean> getExample_sentences() {
        return example_sentences;
    }

    public void setExample_sentences(List<ExampleSentencesBean> example_sentences) {
        this.example_sentences = example_sentences;
    }

    public List<Boolean> getExam_grading() {
        return exam_grading;
    }

    public void setExam_grading(List<Boolean> exam_grading) {
        this.exam_grading = exam_grading;
    }

    public static class ExampleSentencesBean {
        private String en;
        private String zh;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getZh() {
            return zh;
        }

        public void setZh(String zh) {
            this.zh = zh;
        }
    }

    @Override
    public String toString() {
        return "SingleWord{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", root=" + root +
                ", phonetic='" + phonetic + '\'' +
                ", pronunciation_url='" + pronunciation_url + '\'' +
                ", meaning='" + meaning + '\'' +
                ", zh_source='" + zh_source + '\'' +
                ", variation='" + variation + '\'' +
                ", nextTime='" + nextTime + '\'' +
                ", status=" + status +
                ", example_sentences=" + example_sentences +
                ", exam_grading=" + exam_grading +
                '}';
    }
}
