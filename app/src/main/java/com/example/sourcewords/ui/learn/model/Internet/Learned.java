package com.example.sourcewords.ui.learn.model.Internet;

import java.io.Serializable;
import java.util.List;

//TODO 发送网络请求的Body
public class Learned{

    private List<Integer> words;

    public Learned(List<Integer> integers){
        this.words = integers;
    }
}
