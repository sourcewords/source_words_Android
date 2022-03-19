package com.example.sourcewords.ui.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourcewords.R;

public class UserAgreementActivity extends AppCompatActivity {

    private ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useragreement);

        back = findViewById(R.id.user_agreement_back);

        back.setOnClickListener(v ->
                finish());

        getWindow().getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
