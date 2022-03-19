package com.example.sourcewords.ui.main;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sourcewords.R;

//TODO 开机动画 引导页
public class MainActivity extends AppCompatActivity {
    private boolean isAdd = false;
    private long lastBackTime = 0;
    private MainFragment mainFragment;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.setLog(true);

        ActivityLifeCycle lifecycleCallbacks = new ActivityLifeCycle();
        registerActivityLifecycleCallbacks(lifecycleCallbacks);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if(savedInstanceState != null) {
            isAdd = savedInstanceState.getBoolean("isAdd");
        }

        if(!isAdd) {
            addMainFragment();
        }
        //WordRootRepository repository = new WordRootRepository(this);
        //repository.initWordRootList();

    }

    private void addMainFragment() {
        if(mainFragment == null)
            mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, mainFragment, "mainFragment")
                .commit();
        isAdd = true;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackTime <= 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
            lastBackTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("isAdd", isAdd);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}