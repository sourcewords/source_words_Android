package com.example.sourcewords.ui.learn.Internet;

//TODO 发送网络请求的Body
public class Learned{

    /**
     * root_id : 0
     * status : 0
     */

    private int root_id;
    private int status;

    public Learned(int root_id, int status){
        this.root_id = root_id;
        this.status = status;
    }

    public void setRoot_id(int root_id) {
        this.root_id = root_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRoot_id() {
        return root_id;
    }

    public int getStatus() {
        return status;
    }
}
