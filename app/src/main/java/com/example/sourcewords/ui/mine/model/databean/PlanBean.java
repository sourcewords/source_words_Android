package com.example.sourcewords.ui.mine.model.databean;

public class PlanBean {

    public PlanBean(){}
    public PlanBean(String planName, String leastTime, String b_eTime, int progress, int type) {
        this.planName = planName;
        this.leastTime = leastTime;
        this.b_eTime = b_eTime;
        this.progress = progress;
        this.type = type;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getLeastTime() {
        return leastTime;
    }

    public void setLeastTime(String leastTime) {
        this.leastTime = leastTime;
    }

    public String getB_eTime() {
        return b_eTime;
    }

    public void setB_eTime(String b_eTime) {
        this.b_eTime = b_eTime;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private String planName;
    private String leastTime;
    private String b_eTime;
    private int progress;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

}
