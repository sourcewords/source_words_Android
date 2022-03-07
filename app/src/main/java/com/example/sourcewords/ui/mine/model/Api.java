package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;
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
    interface getPlan{
        void success(PlanItem planItem);
        void failed();
    }
    interface changePlanApi{
        void success();
        void failed();
        void requestName();
        void requestTime();
    }

}
