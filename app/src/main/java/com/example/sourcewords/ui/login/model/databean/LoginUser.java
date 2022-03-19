package com.example.sourcewords.ui.login.model.databean;

public class LoginUser {

    private String email;
    private String password;

    public LoginUser(String email, String mPassword) {
        this.email = email;
        this.password = mPassword;
    }

    public String getName() {
        return email;
    }

    public void setName(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
