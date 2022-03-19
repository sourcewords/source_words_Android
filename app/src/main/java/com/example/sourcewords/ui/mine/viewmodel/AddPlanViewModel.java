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

import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.login.model.UserWrapper;
import com.example.sourcewords.ui.login.model.databean.LoginResponse;
import com.example.sourcewords.ui.mine.model.Api;
import com.example.sourcewords.ui.mine.model.databean.AddPlanBean;
import com.example.sourcewords.ui.mine.model.databean.ChoosePlanBean;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlanViewModel extends ViewModel {
    public MutableLiveData<DatePickerDialog> startDatePickerDialog = new MutableLiveData<>();
    public MutableLiveData<DatePickerDialog> endDatePickerDialog = new MutableLiveData<>();
    public MutableLiveData<String> startDate = new MutableLiveData<>();
    public MutableLiveData<String> endDate = new MutableLiveData<>();
    private Context mContext;
    private AddPlanBean addPlanBean = new AddPlanBean();
    String token = UserWrapper.getInstance().getToken();
    int s_y, s_m, s_d, e_y, e_m, e_d;

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
                addPlanBean.setStart(year + "." + month + "." + day);
            }
        };

        s_y = LocalDate.now().getYear();
        s_m = LocalDate.now().getMonthValue();
        s_d = LocalDate.now().getDayOfMonth();

        int style = AlertDialog.THEME_HOLO_LIGHT;

        startDatePickerDialog.setValue(new DatePickerDialog(mContext,style,dateSetListener,s_y,s_m,s_d));
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
                addPlanBean.setEnd(year + "." + month + "." + day);
            }
        };

        e_y = LocalDate.now().getYear();
        e_m = LocalDate.now().getMonthValue();
        e_d = LocalDate.now().getDayOfMonth();

        int style = AlertDialog.THEME_HOLO_LIGHT;

        endDatePickerDialog.setValue(new DatePickerDialog(mContext,style,dateSetListener,e_y,e_m,e_d));
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
    public void addPlan(String name, Api.changePlanApi api){
        addPlanBean.setName(name);
        if(addPlanBean.getName() != null && addPlanBean.getStart() != null && addPlanBean.getEnd() != null){
            NetUtil.getInstance().getApi().changePlan(token, addPlanBean).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.code() == 200){
                        api.success();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    api.failed();
                }
            });
        }else if(addPlanBean.getName() == null){
            api.requestName();
        }else{
            api.requestTime();
        }
    }

    public void changePlan(Api.changePlanApi api, Integer id){
        NetUtil.getInstance().getApi().choosePlan(token, new ChoosePlanBean(id)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() == 200){
                    api.success();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                    api.failed();
            }
        });
    }

}
