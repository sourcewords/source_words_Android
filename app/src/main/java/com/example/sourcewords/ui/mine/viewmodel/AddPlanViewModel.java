package com.example.sourcewords.ui.mine.viewmodel;

import android.app.DatePickerDialog;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPlanViewModel extends ViewModel {
    public MutableLiveData<DatePickerDialog> datePickerDialog = new MutableLiveData<>();

    public void onClick(View view) {
//        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener()
//        {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day)
//            {
//                month = month + 1;
//            }
//        };
//        datePickerDialog= new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,dateSetListener,year,month,day);
    }
}
