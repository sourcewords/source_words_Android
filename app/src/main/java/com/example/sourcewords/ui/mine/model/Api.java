package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.ui.mine.model.databean.PlanBean;

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
