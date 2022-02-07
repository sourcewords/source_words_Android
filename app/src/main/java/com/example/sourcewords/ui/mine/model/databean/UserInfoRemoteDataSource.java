package com.example.sourcewords.ui.mine.model.databean;

public class UserInfoRemoteDataSource {

    private static UserInfoRemoteDataSource INSTANCE;

    private final String token = UserWrapper.getInstance().getToken();

    public static UserInfoRemoteDataSource getInstance(){
        if(INSTANCE == null){
            synchronized (UserInfoRemoteDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new UserInfoRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private UserInfoRemoteDataSource(){};

    public void getRemoteUserInfo()
}
