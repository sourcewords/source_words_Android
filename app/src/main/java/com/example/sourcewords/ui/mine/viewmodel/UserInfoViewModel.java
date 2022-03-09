package com.example.sourcewords.ui.mine.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.UserInfo;
import com.example.sourcewords.ui.mine.model.UserInfoRemoteDataSource;
import com.example.sourcewords.ui.mine.model.databean.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends ViewModel {

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Phone = new MutableLiveData<>();
    public MutableLiveData<Integer> Gender = new MutableLiveData<>();
    public MutableLiveData<String> Birth = new MutableLiveData<>();
    public MutableLiveData<String> Location = new MutableLiveData<>();
    public MutableLiveData<String> Signature = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();

    public MutableLiveData<UserInfo> userMutableLiveData = new MutableLiveData<>();
    private String token = UserWrapper.getInstance().getToken();

    public MutableLiveData<UserInfo> getUserInfo() {
        if(userMutableLiveData == null){
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void changeUserInfo(Api.ChangeUserInfoApi api, UserInfo userInfo){
        NetUtil.getInstance().getApi().putUserInfo(userInfo, token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                api.success();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                api.failed();
            }
        });
    }

    public void initW(){
        UserInfoRemoteDataSource.getInstance().getUserStatus(new Api.getUserInfo.LoadUserCallBack() {
            @Override
            public void onUserLoaded(UserInfoResponse.DataDTO u) {
                if(u != null){
                    Name.setValue(u.getUsername());
                    Phone.setValue(u.getPhone());
                    Gender.setValue(u.getGender());
                    Birth.setValue(u.getBirthday());
                    Location.setValue(u.getLocation());
                    Signature.setValue(u.getSignature());
                }

            }

            @Override
            public void onDataNotAvailable() {

            }

            @Override
            public void onFailure() {

            }
        });

        UserInfo userInfo = new UserInfo(UserWrapper.getInstance().getUser().getAccount(),Gender.getValue(),Phone.getValue(), Signature.getValue() , Location.getValue(), Name.getValue(),Birth.getValue());
        userMutableLiveData.setValue(userInfo);
    }
}
