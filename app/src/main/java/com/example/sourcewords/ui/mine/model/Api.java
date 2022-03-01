package com.example.sourcewords.ui.mine.model;

public interface Api {

    interface ChangePwdApi{
        void success();
        void failed();
    }
    interface ChangeUserInfoApi{
        void success();
        void failed();
    }
}
