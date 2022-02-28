package com.example.sourcewords.ui.review.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.learn.viewModel.LearnViewModel;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;


//TODO 习模块
@RequiresApi(api = Build.VERSION_CODES.N)
public class ReviewFragment extends Fragment {
    private ImageView search;
    private FrameLayout frameLayout;
    private LearnViewModel viewModel;
    private MutableLiveData<Boolean> learnFlag;
    private NoneFragment noneFragment;
    private ReciteFragment reciteFragment;
    private FragmentManager fragmentManager;

    private static int count = 0;
    private boolean hasInit = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this.getActivity()).get(LearnViewModel.class);
        learnFlag = viewModel.getLearnFlag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review,null);
        frameLayout = view.findViewById(R.id.review_container);
        noneFragment = new NoneFragment();

        fragmentManager = getChildFragmentManager();
        viewModel.getLearnFlag().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
                reciteFragment = new ReciteFragment();
                initWordView(reciteFragment);
                Log.d("fragmentManager","2" + bool);
                hasInit = true;

            }
        });
        if(!viewModel.getLearnFlag().getValue())
            initNoneView();
        return view;
    }

    public  void initViewAndListener(){
        search.findViewById(R.id.review_search);
        search.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(),ReviewSearchActivity.class);
            startActivity(intent);
        });
    }


    public void initNoneView() {
        Log.d("fragmentManager","1");
        fragmentManager.beginTransaction().add(R.id.review_container,noneFragment,"NoneFragment")
                .commit();
    }

    public void initWordView(ReciteFragment reciteFragment) {
        if(hasInit){
            fragmentManager.beginTransaction().hide(noneFragment).commit();
            fragmentManager.beginTransaction().add(R.id.review_container,reciteFragment,"ReciteFragment")
                    .commit();
        }

    }
}
