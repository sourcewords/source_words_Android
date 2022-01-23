package com.example.sourcewords;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getAppContext();
//
//        //设置状态栏透明
//        makeStatusBarTransparent(this);
//        //状态栏文字自适应
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }


    // 全局方法需要Context变量时调用
    public static Context getAppContext(){
        return context;
    }
}
