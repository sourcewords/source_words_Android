package com.example.sourcewords;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getAppContext();
    }


    // 全局方法需要Context变量时调用
    public static Context getAppContext(){
        return context;
    }
}
