package com.example.sourcewords.ui.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.sourcewords.R;


//TODO 我模块
public class MineFragment extends Fragment {
    private ConstraintLayout mPersonalData,mAdjustmentPlan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,null);
        init(view);

        return view;
    }

    public void init(View view){
        mPersonalData = view.findViewById(R.id.personal_data);
        mPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserInfoActivity.class);
                startActivity(intent);
            }
        });

        mAdjustmentPlan = view.findViewById(R.id.adjustment_plan);
        mAdjustmentPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddPlanActivity.class);
                startActivity(intent);
            }
        });
    }
}
