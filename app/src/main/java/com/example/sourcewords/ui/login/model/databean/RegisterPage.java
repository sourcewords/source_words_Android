package com.example.sourcewords.ui.login.model.databean;

import android.util.Patterns;

public class RegisterPage {

    private String email;
    private String vercode;
    private String pwd;

    public RegisterPage(String email, String vercode, String pwd) {
        this.email = email;
        this.vercode = vercode;
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isAccountValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }
}
