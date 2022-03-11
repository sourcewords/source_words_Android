package com.example.sourcewords.ui.mine.viewmodel;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;

public class AddPlanViewModel extends ViewModel {
    public MutableLiveData<DatePickerDialog> startDatePickerDialog = new MutableLiveData<>();
    public MutableLiveData<DatePickerDialog> endDatePickerDialog = new MutableLiveData<>();
    public MutableLiveData<String> startDate = new MutableLiveData<>();
    public MutableLiveData<String> endDate = new MutableLiveData<>();
    private Context mContext;

//    public MutableLiveData<LocalPage> getUser() {
//
//        if (userMutableLiveData == null) {
//            userMutableLiveData = new MutableLiveData<>();
//        }
//
//        return userMutableLiveData;
//    }


    public AddPlanViewModel(Context context){
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initStartDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day)
            {
                month = month + 1;
                LocalDate date = LocalDate.of(year, month, day);
                startDate.setValue(date.toString());
            }
        };

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        int style = AlertDialog.THEME_HOLO_LIGHT;

        startDatePickerDialog.setValue(new DatePickerDialog(mContext,style,dateSetListener,year,month,day));
        startDatePickerDialog.getValue().getDatePicker().setMinDate(System.currentTimeMillis());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initEndDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day)
            {
                month = month + 1;
                LocalDate date = LocalDate.of(year, month, day);
                endDate.setValue(date.toString());
            }
        };

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        int style = AlertDialog.THEME_HOLO_LIGHT;

        endDatePickerDialog.setValue(new DatePickerDialog(mContext,style,dateSetListener,year,month,day));
        endDatePickerDialog.getValue().getDatePicker().setMinDate(System.currentTimeMillis());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openStartDatePicker(View view) {
        initStartDatePicker();
        startDatePickerDialog.getValue().show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openEndDatePicker(View view) {
        initEndDatePicker();
        endDatePickerDialog.getValue().show();
    }

}
