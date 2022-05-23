package com.example.sourcewords.ui.mine.model;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.mine.model.databean.UserInfoResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserInfoRemoteDataSource implements Api.getUserInfo{

    private static UserInfoRemoteDataSource INSTANCE;
    private UserInfoResponse.DataDTO u;

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

    @Override
    public void getUserStatus(LoadUserCallBack loadUserCallBack) {
        NetUtil.getInstance().getApi().getUserInfo(UserWrapper.getInstance().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoResponse userInfoResponse) {
                        u = userInfoResponse.getData();
                        loadUserCallBack.onUserLoaded(u);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadUserCallBack.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
