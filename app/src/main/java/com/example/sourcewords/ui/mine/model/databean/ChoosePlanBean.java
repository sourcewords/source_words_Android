package com.example.sourcewords.ui.mine.model.databean;

public class ChoosePlanBean {


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    private String start;

    public ChoosePlanBean(String start, Integer planId, String end) {
        this.start = start;
        this.planId = planId;
        this.end = end;
    }

    private Integer planId;
    private String end;
}
