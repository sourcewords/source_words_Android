package com.example.sourcewords.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.model.WordRepository;


//TODO 开机动画 引导页
public class MainActivity extends AppCompatActivity {

    private long lastBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        addMainFragment();
        //WordRootRepository repository = new WordRootRepository(this);
        //repository.initWordRootList();


    }

    private void addMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new MainFragment(), "mainFragment")
                .commit();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}