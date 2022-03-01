package com.example.sourcewords.ui.mine.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.ui.mine.model.PlanDataResource;
import com.example.sourcewords.ui.mine.model.databean.PlanBean;

public class MyPlanViewModel extends ViewModel {

    public MutableLiveData<PlanBean> myPlan;

    public MutableLiveData<PlanBean> getMyPlan(){
        if(myPlan == null){
            myPlan = new MutableLiveData<>();
            myPlan.setValue(PlanDataResource.getInstance().getMyPlan());
        }
        return myPlan;
    }
}
