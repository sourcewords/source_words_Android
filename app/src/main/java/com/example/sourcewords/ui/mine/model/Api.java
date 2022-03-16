package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.ui.mine.model.databean.PlanItem;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;
import com.example.sourcewords.ui.mine.model.databean.UserInfoResponse;

public interface Api {

    interface ChangePwdApi{
        void success();
        void failed();
    }
    interface ChangeUserInfoApi{
        void success();
        void failed();
    }
    interface getUserInfo{
        interface  LoadUserCallBack{
            void onUserLoaded(UserInfoResponse.DataDTO u);

            void onDataNotAvailable();

            void onFailure();
        }

        void getUserStatus(LoadUserCallBack loadUserCallBack);
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
    interface addPlan{
        void success(String name);
        void failed();
    }

}
