package com.example.sourcewords.ui.mine.model.databean;

import java.util.List;

public class PlanItem {
    private Integer code;
    private String message;
    private DataDTO data;

    public static class DataDTO {
        private Integer userID;
        private List<PlansDTO> plans;

        public static class PlansDTO {
            private Integer uid;
            private String name;
            private String start;
            private String end;
            private String word;
            private String img;
            private Integer percent;
        }
    }
}
