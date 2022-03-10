package com.example.sourcewords.ui.login.model;

public class User {
    private String account;
    private String password;
    private String token;

    public User(){}
    public User(String account, String password, String token) {
        this.account = account;
        this.password = password;
        this.token = token;

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
