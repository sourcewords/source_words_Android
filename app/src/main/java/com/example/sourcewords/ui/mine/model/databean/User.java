package com.example.sourcewords.ui.mine.model.databean;

public class User {

    private String password;
    private String name;
    private String token;

    public User(){}

    public User(String password, String name, String token){
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
