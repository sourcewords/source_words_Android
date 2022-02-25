package com.example.sourcewords.ui.login.model.databean;

public class LoginUser {

    private String name;
    private String password;

    public LoginUser(String name, String mPassword) {
        this.name = name;
        this.password = mPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
