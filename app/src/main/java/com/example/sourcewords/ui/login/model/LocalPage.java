package com.example.sourcewords.ui.login.model;

import android.util.Patterns;

public class LocalPage {

    private String name;
    private String password;
    private boolean checked;

    public LocalPage(String name, String password) {
        this.name = name;
        this.password = password;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isAccountValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getName()).matches();
    }

    public boolean isPasswordLengthGreaterThan3() {
        return getPassword().length() > 3;
    }
}
