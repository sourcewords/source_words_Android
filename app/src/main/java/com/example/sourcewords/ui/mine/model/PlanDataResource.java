package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.PlanItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanDataResource {

    private static PlanDataResource INSTANCE;
    private PlanItem myplan = new PlanItem();
    private final String token = UserWrapper.getInstance().getToken();

    public static PlanDataResource getInstance() {
        if(INSTANCE == null){
            synchronized (PasswordDataSource.class) {
                if( INSTANCE == null){
                    INSTANCE = new PlanDataResource();
                }
            }
        }
        return INSTANCE;
    }

    private PlanDataResource (){}

    public void getMyPlan(Api.getPlan api){
        NetUtil.getInstance().getApi().getMyPlan(token).enqueue(new Callback<PlanItem>() {
            @Override
            public void onResponse(Call<PlanItem> call, Response<PlanItem> response) {
                if(response.code() == 200){
                    myplan = response.body();
                    api.success(myplan);
                }
            }

            @Override
            public void onFailure(Call<PlanItem> call, Throwable t) {
                api.failed();
            }
        });
    }
}
