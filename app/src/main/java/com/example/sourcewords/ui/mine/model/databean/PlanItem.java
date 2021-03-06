package com.example.sourcewords.ui.mine.model.databean;

import java.util.List;


public class PlanItem {

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    private Integer code;
    private String message;
    private DataDTO data;

    public static class DataDTO {
        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public List<PlansDTO> getPlans() {
            return plans;
        }

        public void setPlans(List<PlansDTO> plans) {
            this.plans = plans;
        }

        private Integer userID;
        private List<PlansDTO> plans;

        public static class PlansDTO {
            private Integer plan_id;
            private Integer uid;
            private String name;
            private String start;
            private String end;

            public Integer getPlanId() {
                return plan_id;
            }

            public void setPlanId(Integer planId) {
                this.plan_id = planId;
            }

            public Integer getUid() {
                return uid;
            }

            public void setUid(Integer uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public Integer getActive() {
                return active;
            }

            public void setActive(Integer active) {
                this.active = active;
            }

            public Double getPercent() {
                return percent;
            }

            public void setPercent(Double percent) {
                this.percent = percent;
            }

            private String word;
            private String img;
            private Integer active;
            private Double percent;
        }
    }
}
