package com.example.sourcewords.ui.mine.model.databean;

import java.util.List;

public class SigninBean {
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

        public Integer getAll() {
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }

        private Integer userID;
        private List<PlansDTO> plans;
        private Integer all;

        public static class PlansDTO {
            public Integer getUserid() {
                return userid;
            }

            public void setUserid(Integer userid) {
                this.userid = userid;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            private Integer userid;
            private String data;
        }
    }
}
