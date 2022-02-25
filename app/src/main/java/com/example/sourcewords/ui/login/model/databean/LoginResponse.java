package com.example.sourcewords.ui.login.model.databean;

public class LoginResponse {
    /*
    * "code": 0
    * "message": "OK"
    * "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDQ0MTcxNDYsImlhdCI6MTY0NDMzMDc0NiwidWlkIjoxOH0.ACa2VE8DRv6qO3iHMEsMbYRus6wCh9m8IF18NIKMPho"
    * */

    private int code;
    private String msg;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
