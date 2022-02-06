package com.example.sourcewords.ui.review.dataBean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class WordRoot {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int ID;
    /**
     * meaning : quis sunt
     * video_url : http://ityh.gu/zlokiexhl
     * id : 78
     * source : adipisicing ipsum cillum dolor
     * root : in pariatur
     */
    @ColumnInfo(name = "中文意思")
    private String meaning;
    @ColumnInfo(name = "视频地址")
    private String video_url;
    @ColumnInfo(name = "词根序号")
    private int id;
    @ColumnInfo(name = "词根来源")
    private String source;
    @ColumnInfo(name = "词根")
    private String root;
    @ColumnInfo(name = "单词列表")
    private List<Word> wordlist;

    public WordRoot(int ID, String meaning, String video_url, int id, String source, String root, List<Word> wordlist) {
        this.ID = ID;
        this.meaning = meaning;
        this.video_url = video_url;
        this.id = id;
        this.source = source;
        this.root = root;
        this.wordlist = wordlist;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Word> getWordlist() {
        return wordlist;
    }

    public void setWordlist(List<Word> wordlist) {
        this.wordlist = wordlist;
    }
    /*
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "英文词根")
    private String englishRoot;
    @ColumnInfo(name = "词根意思")
    private String meaningOfRoot;
    @ColumnInfo(name = "单词表")
    private List<Word> list;

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
     */
}
