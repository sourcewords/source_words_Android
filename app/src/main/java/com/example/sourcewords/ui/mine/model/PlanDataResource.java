package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanDataResource {

    private static PlanDataResource INSTANCE;

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

    public PlanBean getMyPlan(){
        final PlanBean[] myplan = {new PlanBean()};
        NetUtil.getInstance().getApi().getMyPlan(token).enqueue(new Callback<PlanBean>() {
            @Override
            public void onResponse(Call<PlanBean> call, Response<PlanBean> response) {
                myplan[0] = response.body();
            }

            @Override
            public void onFailure(Call<PlanBean> call, Throwable t) {

            }
        });
        return myplan[0];
    }
}
