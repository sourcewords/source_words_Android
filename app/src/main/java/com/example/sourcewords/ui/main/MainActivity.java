package com.example.sourcewords.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.sourcewords.R;


//TODO 开机动画 引导页
public class MainActivity extends AppCompatActivity {

    private long lastBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMainFragment();
    }

    private void addMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container,new MainFragment(),"mainFragment")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (System.currentTimeMillis()-lastBackTime<=2000){
            super.onBackPressed();
        }else {
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_LONG).show();
            lastBackTime=System.currentTimeMillis();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}