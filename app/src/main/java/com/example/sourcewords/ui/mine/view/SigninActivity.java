package com.example.sourcewords.ui.mine.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.SigninDateSource;
import com.example.sourcewords.ui.mine.model.databean.Decorator;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class SigninActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView signInDay;
    private Button signIn;
    private List<CalendarDay> dates;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_signin);

        signInDay = findViewById(R.id.signin_day);
        signInDay.setText(String.valueOf(SigninDateSource.getInstance().getSigninBean().getData().getAll()));
        //dates = SigninDateSource.getInstance().getSigninBean().getData().getPlans(
        signIn = findViewById(R.id.sign_in_btn);
        signIn.setOnClickListener(v -> {
            String date;
            date = String.format(CalendarDay.today().toString(), "YYYY.MM.DD");
            SigninDateSource.getInstance().putSignInDate(date, new Api.SignInApi() {
                @Override
                public void success() {
                    Toast.makeText(SigninActivity.this, "签到成功！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failed() {

                }
            });
        });
        calendarView = findViewById(R.id.calendar);
        HashSet<CalendarDay> dates = new HashSet<>();
        dates.add(new CalendarDay(2022, 3, 2));
        Decorator decorator = new Decorator(Color.RED, dates);
        calendarView.addDecorator(decorator);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
