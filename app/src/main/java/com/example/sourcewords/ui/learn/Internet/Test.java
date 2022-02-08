package com.example.sourcewords.ui.learn.Internet;

import java.io.Serializable;
import java.util.List;
//TODO 网络请求的接受类，以后再说
public class Test implements Serializable {

    /**
     * code : 0
     * message : OK
     * data : [{"id":10,"root":"sist","meaning":"站立 "},{"id":22,"root":"ras","meaning":"刮擦 "},{"id":23,"root":"soph","meaning":"智慧，聪明 "},{"id":26,"root":"aster","meaning":"星星 "}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{
        /**
         * id : 10
         * root : sist
         * meaning : 站立
         */

        private int id;
        private String root;
        private String meaning;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }
    }
}
