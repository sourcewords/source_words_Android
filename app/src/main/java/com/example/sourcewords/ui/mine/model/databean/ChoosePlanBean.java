package com.example.sourcewords.ui.mine.model.databean;


public class ChoosePlanBean {
    public ChoosePlanBean(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    private Integer planId;
}
