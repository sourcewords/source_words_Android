package com.example.sourcewords.ui.review.dataBean;

import java.util.List;
//网络请求的/words/list的基础接受类
public class WordInfoBean {
    private String zh_source;
    private String pronunciation_url;
    private String meaning;
    private String phonetic;
    private int id;
    private int root;
    private String variation;
    private String word;
    private List<Boolean> exam_grading;
    /**
     * zh : in est Lorem sed ad
     * en : ad ea quis amet cupidatat
     */

    private List<ExampleSentencesBean> example_sentences;

    public String getZh_source() {
        return zh_source;
    }

    public void setZh_source(String zh_source) {
        this.zh_source = zh_source;
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

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Boolean> getExam_grading() {
        return exam_grading;
    }

    public void setExam_grading(List<Boolean> exam_grading) {
        this.exam_grading = exam_grading;
    }

    public List<ExampleSentencesBean> getExample_sentences() {
        return example_sentences;
    }

    public void setExample_sentences(List<ExampleSentencesBean> example_sentences) {
        this.example_sentences = example_sentences;
    }

    public static class ExampleSentencesBean {
        private String zh;
        private String en;

        public ExampleSentencesBean(){}

        public ExampleSentencesBean(String zh,String en){
            this.zh = zh;
            this.en = en;
        }

        public String getZh() {
            return zh;
        }

        public void setZh(String zh) {
            this.zh = zh;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }
}
