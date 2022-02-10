package com.example.sourcewords.ui.review.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;


//TODO 习模块
public class ReviewFragment extends Fragment {

    private WordRepository wordRepository;

    private FrameLayout frameLayout;
    private ReviewCardViewModel reviewCardViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordRepository = new WordRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review,null);
        frameLayout = view.findViewById(R.id.review_container);
        initWordView(wordRepository.getWordRootTest2(1));
        return view;
    }


    public void initNoneView() {
        getChildFragmentManager().beginTransaction().add(R.id.review_container,new NoneFragment(),"NoneFragment")
                .commit();
    }

    public void initWordView(WordRoot wordRoot) {
        getChildFragmentManager().beginTransaction().add(R.id.review_container,new ReciteFragment(wordRoot),"ReciteFragment")
                .commit();
    }
}
