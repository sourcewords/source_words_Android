package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanDataResource {

    private static PlanDataResource INSTANCE;
    private PlanBean myplan;
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

    public void getMyPlan(){
        NetUtil.getInstance().getApi().getMyPlan(token).enqueue(new Callback<PlanBean>() {
            @Override
            public void onResponse(Call<PlanBean> call, Response<PlanBean> response) {
                 myplan = response.body();
            }

            @Override
            public void onFailure(Call<PlanBean> call, Throwable t) {

            }
        });
    }

    public PlanBean getMyplan(){
        getMyPlan();
        return myplan;
    }
}
