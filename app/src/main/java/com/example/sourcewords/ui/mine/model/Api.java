package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;

public interface Api {

    interface ChangePwdApi{
        void success();
        void failed();
    }
    interface ChangeUserInfoApi{
        void success();
        void failed();
    }
    interface putSignInApi{
        void success();
        void failed();
    }
    interface getSignInApi{
        void success(SigninBean signinBean);
        void failed();
    }

}
