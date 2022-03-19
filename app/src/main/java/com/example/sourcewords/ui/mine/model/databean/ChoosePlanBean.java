package com.example.sourcewords.ui.mine.model.databean;


public class ChoosePlanBean {
    public ChoosePlanBean(Integer plan_id) {
        this.plan_id = plan_id;
    }

    public Integer getPlanId() {
        return plan_id;
    }

    public void setPlanId(Integer plan_id) {
        this.plan_id = plan_id;
    }

    private Integer plan_id;
}
