package com.example.sourcewords.ui.review.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.ReviewContract;
import com.example.sourcewords.ui.review.ReviewPresenter;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.WordRepository;
import com.example.sourcewords.ui.review.view.NoneFragment;
import com.example.sourcewords.ui.review.view.ReciteFragment;


//TODO 习模块
public class ReviewFragment extends Fragment implements ReviewContract.View {

    private WordRepository wordRepository;
    private ReviewContract.Presenter presenter;

    private FrameLayout frameLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordRepository = new WordRepository(getContext());
        presenter = new ReviewPresenter(wordRepository, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review,null);
        frameLayout = view.findViewById(R.id.review_container);
        presenter.initData();
        return view;
    }

    @Override
    public void setPresenter(ReviewContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initNoneView() {
//        getFragmentManager().beginTransaction()
//                .add(R.id.main_container, new MainFragment(), "mainFragment")
//                .commit();
        getChildFragmentManager().beginTransaction().add(R.id.review_container,new NoneFragment(),"NoneFragment")
                .commit();
    }

    @Override
    public void initWordView(WordRoot wordRoot) {
        getChildFragmentManager().beginTransaction().add(R.id.review_container,new ReciteFragment(wordRoot),"ReciteFragment")
                .commit();
    }
}
