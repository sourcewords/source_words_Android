package com.example.sourcewords.ui.login.model;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.example.sourcewords.commonUtils.Encryption;
import com.example.sourcewords.commonUtils.SPUtils;

public class UserWrapper {
    private User user;
    private SPUtils spUtils;
    private static UserWrapper instance=new UserWrapper();
    private Encryption encryption;

    private UserWrapper(){
        spUtils=SPUtils.getInstance(SPUtils.SP_CONFIG);
        encryption=new Encryption();
        String userJson= spUtils.getString("user","");
        if (userJson.length()==0){
            user=null;
        }
        else {
            try {
                userJson=encryption.decryptAES(userJson);
            } catch (Exception e) {
                e.printStackTrace();
                userJson="";
            }
            if (userJson.length()!=0)
                user = JSONObject.parseObject(userJson, User.class);
        }

    }

    public static UserWrapper getInstance(){
        return instance;
    }

    /**
     *
     * 注意可能为空，小心NPE
     * @return 存储的user，如果没有则return null,
     * 表示还没有登录
     *
     */
    @Nullable
    public User getUser(){
        return user;

    }

    public void setUser(User user){
        this.user=user;
        String json= JSONObject.toJSONString(user);
        try {
            json=encryption.encryptAES(json);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        spUtils.put("user",json,false);
    }


    public String getToken(){
        if (user==null)
            return "";
        return user.getToken();
    }
}
