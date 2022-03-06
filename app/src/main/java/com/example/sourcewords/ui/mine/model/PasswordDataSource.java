package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.respository.LoginRemoteRespository;

public class PasswordDataSource {

    private static PasswordDataSource INSTANCE;

    private final String token = LoginRemoteRespository.getINSTANCE().getToken();

    public static PasswordDataSource getInstance() {
        if(INSTANCE == null){
            synchronized (PasswordDataSource.class) {
                if( INSTANCE == null){
                    INSTANCE = new PasswordDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private PasswordDataSource (){}

    public String getPwd(){
        assert UserWrapper.getInstance().getUser() != null;
        return UserWrapper.getInstance().getUser().getPassword();
    }
}
