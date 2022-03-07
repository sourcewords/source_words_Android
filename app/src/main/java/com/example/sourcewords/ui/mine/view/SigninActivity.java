package com.example.sourcewords.ui.mine.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.sourcewords.R;
import com.example.sourcewords.commonUtils.SPUtils;
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
import java.util.TimeZone;

public class SigninActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView signInDay,time;
    private Button signIn;
    private List<String> datestring = new ArrayList<>();
    private HashSet<CalendarDay> dates = new HashSet<>();
    private SigninBean signin;
    private ImageButton back;

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

        time = findViewById(R.id.signin_time);
        time.setText(String.valueOf(SPUtils.getInstance().getLong("APP_USE_TIME", 0L)/60000));

        back = findViewById(R.id.mine_back);
        back.setOnClickListener(v -> {
            finish();
        });
        calendarView = findViewById(R.id.calendar);

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().
                    setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private String getFormatTime(long app_use_time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        //设置时区，跳过此步骤会默认设置为"GMT+08:00" 得到的结果会多出来8个小时
        format.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        return format.format(app_use_time);
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
                for(String s : datestring) {
                    try {
                        Date date1 = sdf.parse(s);
                        String month = date1.toGMTString().substring(2, 5);
                        int m;
                        switch (month) {
                            case "Jan":
                                m = 1;
                                break;
                            case "Feb":
                                m = 2;
                                break;
                            case "Mar":
                                m = 3;
                                break;
                            case "Apr":
                                m = 4;
                                break;
                            case "May":
                                m = 5;
                                break;
                            case "Jun":
                                m = 6;
                                break;
                            case "Jul":
                                m = 7;
                                break;
                            case "Aug":
                                m = 8;
                                break;
                            case "Sep":
                                m = 9;
                                break;
                            case "Oct":
                                m = 10;
                                break;
                            case "Nov":
                                m = 11;
                                break;
                            default:
                                m = 12;
                                break;

                        }
                        String year = date1.toGMTString().substring(6, 10);
                        int y = Integer.parseInt(year);
                        CalendarDay date = CalendarDay.from(y, m, date1.getDate());

                        dates.add(new CalendarDay(date.getYear(), date.getMonth(), date.getDay()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Decorator decorator = new Decorator(Color.RED, dates);
                calendarView.addDecorator(decorator);
            }

            @Override
            public void failed() {

            }
        });
    }

//    private void back(View v){
//        onBackPressed();
//    }
}
