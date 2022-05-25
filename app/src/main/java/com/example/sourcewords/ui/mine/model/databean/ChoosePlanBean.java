package com.example.sourcewords.ui.mine.model.databean;

public class ChoosePlanBean {


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Integer getPlanId() {
        return plan_id;
    }

    public void setPlanId(Integer planId) {
        this.plan_id = plan_id;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    private String start;

    public ChoosePlanBean(String start, Integer plan_id, String end) {
        this.start = start;
        this.plan_id = plan_id;
        this.end = end;
    }

    private Integer plan_id;
    private String end;
}
