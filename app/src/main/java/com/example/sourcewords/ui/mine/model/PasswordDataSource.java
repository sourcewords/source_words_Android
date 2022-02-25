package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.ui.mine.model.databean.UserWrapper;

public class PasswordDataSource {

    private static PasswordDataSource INSTANCE;

    private final String token = UserWrapper.getInstance().getToken();

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

    public void getPwd(){}
}
