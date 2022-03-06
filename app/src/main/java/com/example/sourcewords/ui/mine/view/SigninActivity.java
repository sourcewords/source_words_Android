package com.example.sourcewords.ui.mine.view;

import android.annotation.SuppressLint;
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
import com.example.sourcewords.ui.mine.model.databean.PlanBean;
import com.example.sourcewords.ui.mine.model.databean.SigninBean;
import com.example.sourcewords.ui.mine.model.databean.SigninDate;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class SigninActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView signInDay;
    private Button signIn;
    private List<String> datestring = new ArrayList<>();
    private HashSet<CalendarDay> dates = new HashSet<>();
    private SigninBean signin;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_signin);

        signInDay = findViewById(R.id.signin_day);
        initData();

        signIn = findViewById(R.id.sign_in_btn);
        signIn.setOnClickListener(v -> {
            String date = CalendarDay.today().getYear() + "." + CalendarDay.today().getMonth() + "." + CalendarDay.today().getDay();
            SigninDateSource.getInstance().putSignInDate(new SigninDate(date), new Api.putSignInApi() {
                @Override
                public void success() {
                    Toast.makeText(SigninActivity.this, "签到成功！", Toast.LENGTH_SHORT).show();
                    initData();
                }

                @Override
                public void failed() {

                }
            });
        });
        calendarView = findViewById(R.id.calendar);

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    void initData(){
        SigninDateSource.getInstance().getAllSignDate(new Api.getSignInApi() {
            @Override
            public void success(SigninBean signinBean) {
                dates.clear();
                signin = signinBean;
                signInDay.setText(String.valueOf(signin.getData().getAll()));
                for(SigninBean.DataDTO.PlansDTO p : signin.getData().getPlans()){
                    datestring.add(p.getData());
                }
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy.MM.dd" );
                for(String s : datestring){
                    try {
                        Date date1 = sdf.parse(s);
                        CalendarDay date = CalendarDay.from(date1.getYear(), date1.getMonth(), date1.getDay());
                        dates.add(new CalendarDay(date.getYear(), date.getMonth(), date.getDay()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                dates.add(new CalendarDay(2022, 3, 1));
                Decorator decorator = new Decorator(Color.RED, dates);
                calendarView.addDecorator(decorator);
            }

            @Override
            public void failed() {

            }
        });
    }
}
