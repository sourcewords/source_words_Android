package com.example.sourcewords.ui.mine.model.databean;

import java.util.List;

public class SigninBean {
    private Integer code;
    private String message;
    private DataDTO data;

    public static class DataDTO {
        private Integer userID;
        private List<PlansDTO> plans;
        private Integer all;

        public static class PlansDTO {
            private Integer userid;
            private String data;
        }
    }
}
