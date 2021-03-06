package com.example.sourcewords.ui.review.view;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sourcewords.App;
import com.example.sourcewords.R;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordCardState;
import com.example.sourcewords.ui.review.dataBean.WordInfoBean;
import com.example.sourcewords.ui.review.view.reviewUtils.ContextUtils;
import com.example.sourcewords.ui.review.view.reviewUtils.ReciteCardConverter;
import com.example.sourcewords.ui.review.view.reviewUtils.WordSample;
import com.example.sourcewords.ui.review.viewmodel.ReviewCardViewModel;
import com.example.sourcewords.utils.DateUtils;
import com.example.sourcewords.utils.PreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReciteFragment extends Fragment {
    private Word word;
    private MutableLiveData<WordSample> wordSample;
    private FloatingActionButton button;
    private TextView wordEng;
    private TextView soundMark;
    private TextView newLearned,haveLearned,review;
    private MutableLiveData<Integer> newLearnedCount,haveLearnedCount,reviewCount;
    private ReviewCardViewModel reviewCardViewModel;

    private ImageView newLearnedBkg, haveLearnedBkg, reviewBkg;

    private CardView mWordCard;
    private int status;
    private int count;
    /**
     * ???????????? ??? ???????????????????????? ????????? 0
     * ??????????????????????????????????????????????????????????????? 1
     * ????????????????????????????????????????????????????????? 2
     */
    public static final int WORD_NEW = 0;
    public static final int WORD_TODAY_REVIEW_AGAIN = 1;
    public static final int WORD_PAST_REVIEWED = 2;
    private ViewStub stub;


    public ReciteFragment() {
    }

    private void initData() {
        this.newLearnedCount = reviewCardViewModel.getNewLearnedCount();
        this.haveLearnedCount = reviewCardViewModel.getHaveLearnedCount();
        this.reviewCount = reviewCardViewModel.getReviewCount();
        this.wordSample = reviewCardViewModel.getNextWordSample();
        ContextUtils.setContext(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recite,container,false);
        button = view.findViewById(R.id.next_btn);

//        upload = view.findViewById(R.id.upload_btn);
//        download = view.findViewById(R.id.download_btn);

        wordEng = view.findViewById(R.id.word);
        soundMark = view.findViewById(R.id.sound_mark);
        newLearned = view.findViewById(R.id.new_learn);
        haveLearned = view.findViewById(R.id.have_learned);
        review = view.findViewById(R.id.review);
        mWordCard = view.findViewById(R.id.card);
        stub = view.findViewById(R.id.viewstub_done);

        newLearnedBkg = view.findViewById(R.id.new_learn_bkg);
        haveLearnedBkg = view.findViewById(R.id.have_learned_bkg);
        reviewBkg = view.findViewById(R.id.review_bkg);


        this.reviewCardViewModel = ViewModelProviders.of(getActivity()).get(ReviewCardViewModel.class);
        initData();
        wordSample.observe(getViewLifecycleOwner(), new Observer<WordSample>() {
            @Override
            public void onChanged(WordSample wordSample) {
                initView();
            }
        });
        return view;
    }

    public void hide() {
        stub.inflate();
        mWordCard.setClickable(false);
        button.setClickable(false);
        reviewCardViewModel.loadAllWordsToDataBase();
        reviewCardViewModel.deleteWordCardState();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PreferencesUtils.WORD_ROOT_HAVE_LEARNED, true);
        editor.putString(PreferencesUtils.LAST_REVIEW_DAY, DateUtils.getDate());
        editor.commit();
    }

    private void initView() {
        Log.d("count", " " + newLearnedCount.getValue() + " " + reviewCount.getValue() + " " + haveLearnedCount.getValue());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        Boolean flag = sharedPreferences.getBoolean(PreferencesUtils.WORD_ROOT_HAVE_LEARNED, false);
        if(flag || wordSample == null || newLearnedCount.getValue() == 0 && haveLearnedCount.getValue() == 0 && reviewCount.getValue() == 0) {
            hide();
            return;
        }
        word = wordSample.getValue().getWord();
        Log.d("wordinfo", word.getWord_info().getWord());
        Log.d("wordSample", wordSample.getValue().getWord().getWord_info().getWord()+" " + wordSample.getValue().getWord().getWord());
        WordInfoBean mWordInfo = word.getWord_info();
        status = wordSample.getValue().getStatus();
        if(status == 0) {
            count = reviewCardViewModel.getNewLearnedCount().getValue();
            newLearnedBkg.setVisibility(View.VISIBLE);
            haveLearnedBkg.setVisibility(View.INVISIBLE);
            reviewBkg.setVisibility(View.INVISIBLE);
        }
        else if(status == 2) {
            count = reviewCardViewModel.getHaveLearnedCount().getValue();
            newLearnedBkg.setVisibility(View.INVISIBLE);
            haveLearnedBkg.setVisibility(View.VISIBLE);
            reviewBkg.setVisibility(View.INVISIBLE);
        }
        else {
            count = reviewCardViewModel.getReviewCount().getValue();
            newLearnedBkg.setVisibility(View.INVISIBLE);
            haveLearnedBkg.setVisibility(View.INVISIBLE);
            reviewBkg.setVisibility(View.VISIBLE);
        }

        wordEng.setText(mWordInfo.getWord());
        soundMark.setText(mWordInfo.getPhonetic());
        newLearnedCount.observe(getViewLifecycleOwner(), integer -> newLearned.setText(String.valueOf(integer)));
        haveLearnedCount.observe(getViewLifecycleOwner(),integer -> haveLearned.setText(String.valueOf(integer)));
        reviewCount.observe(getViewLifecycleOwner(), integer -> review.setText(String.valueOf(integer)));

        mWordCard.setOnClickListener(v -> {

            Intent intent = new Intent(App.getAppContext(), DetailActivity.class);
            intent.putExtra("wordId", word.getId());
            intent.putExtra("count",count);

            reviewCardViewModel.setLastLearnTime(DateUtils.getTime());
            Log.d("lastLearnTime", DateUtils.getTime());

            if(status == WORD_NEW) {
                intent.putExtra("code", WORD_NEW);
                startActivityForResult(intent, WORD_NEW);
            }
            else if(status == WORD_TODAY_REVIEW_AGAIN){
                intent.putExtra("code", WORD_TODAY_REVIEW_AGAIN);
                startActivityForResult(intent, WORD_TODAY_REVIEW_AGAIN);
            }
            else if(status == WORD_PAST_REVIEWED){
                intent.putExtra("code", WORD_PAST_REVIEWED);
                startActivityForResult(intent, WORD_PAST_REVIEWED);
            }
        });
        button.setOnClickListener(v -> {
            getPreWord();
        });
        /*
        upload.setOnClickListener(v -> {
            String date = DateUtils.getDate();
            WordCardState wordCardState = new WordCardState(date,reviewCardViewModel.getNewLearnedWords(),
                    reviewCardViewModel.getHaveLearnedWords(), reviewCardViewModel.getReviewWords(),
                    reviewCardViewModel.getNewLearnedCount().getValue(),
                    reviewCardViewModel.getHaveLearnedCount().getValue(),
                    reviewCardViewModel.getReviewCount().getValue(),
                    reviewCardViewModel.getWordSampleMutableLiveData().getValue(),
                    reviewCardViewModel.getWordPool(),
                    reviewCardViewModel.getLastLearnTime(),
                    reviewCardViewModel.getHistoryStack(),
                    reviewCardViewModel.getPriorityQueue(),
                    reviewCardViewModel.getNewLearnedWordsQueue(),
                    reviewCardViewModel.getHaveLearnedWordsQueue());
            reviewCardViewModel.uploadReviewCardState(ReciteCardConverter.ReciteCardState2String(wordCardState));
        });
        download.setOnClickListener(v -> {
            reviewCardViewModel.downloadWordCardState();
        });


        upload.setOnClickListener(v -> {
            String date = DateUtils.getDate();
            WordCardState wordCardState = new WordCardState(date,reviewCardViewModel.getNewLearnedWords(),
                    reviewCardViewModel.getHaveLearnedWords(), reviewCardViewModel.getReviewWords(),
                    reviewCardViewModel.getNewLearnedCount().getValue(),
                    reviewCardViewModel.getHaveLearnedCount().getValue(),
                    reviewCardViewModel.getReviewCount().getValue(),
                    reviewCardViewModel.getWordSampleMutableLiveData().getValue(),
                    reviewCardViewModel.getWordPool(),
                    reviewCardViewModel.getLastLearnTime(),
                    reviewCardViewModel.getHistoryStack(),
                    reviewCardViewModel.getPriorityQueue(),
                    reviewCardViewModel.getNewLearnedWordsQueue(),
                    reviewCardViewModel.getHaveLearnedWordsQueue());
            reviewCardViewModel.uploadReviewCardState(ReciteCardConverter.ReciteCardState2String(wordCardState));
        });
        download.setOnClickListener(v -> {
            reviewCardViewModel.downloadWordCardState();
        });

         */

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            reviewCardViewModel.getNextWordSample();
        }
        else {
            Toast.makeText(App.getAppContext(),
                    "Please make a choice",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getPreWord() {
        if(reviewCardViewModel.getHistoryStack().isEmpty()) return;

        // ????????????????????????????????????
        wordSample.getValue().setStatus(wordSample.getValue().getWord().getWord_info().getStatus());
        switch (wordSample.getValue().getWord().getWord_info().getStatus()) {
            case 0:
                if(!reviewCardViewModel.getNewLearnedWordsQueue().contains(wordSample.getValue()))
                    reviewCardViewModel.getNewLearnedWordsQueue().offer(wordSample.getValue());
                break;
            case 1:
                if(!reviewCardViewModel.getPriorityQueue().contains(wordSample.getValue()))
                    reviewCardViewModel.getPriorityQueue().offer(wordSample.getValue());
                break;
            case 2:
                if(!reviewCardViewModel.getHaveLearnedWordsQueue().contains(wordSample.getValue()))
                    reviewCardViewModel.getHaveLearnedWordsQueue().offer(wordSample.getValue());
                break;
        }
        Log.d("pre", "curWord" + wordSample.getValue().toString());
        // ????????????????????????????????????
        reviewCardViewModel.getPreWord(wordSample.getValue().getWord().getWord_info().getStatus());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // ?????????????????????????????????
        reviewCardViewModel.deleteWordCardState();
        reviewCardViewModel.loadAllWordsToDataBase();
        String date = DateUtils.getDate();
        // ?????????????????????
        WordCardState wordCardState = new WordCardState(date,reviewCardViewModel.getNewLearnedWords(),
                reviewCardViewModel.getHaveLearnedWords(), reviewCardViewModel.getReviewWords(),
                reviewCardViewModel.getNewLearnedCount().getValue(),
                reviewCardViewModel.getHaveLearnedCount().getValue(),
                reviewCardViewModel.getReviewCount().getValue(),
                reviewCardViewModel.getWordSampleMutableLiveData().getValue(),
                reviewCardViewModel.getWordPool(),
                reviewCardViewModel.getLastLearnTime(),
                reviewCardViewModel.getHistoryStack(),
                reviewCardViewModel.getPriorityQueue(),
                reviewCardViewModel.getNewLearnedWordsQueue(),
                reviewCardViewModel.getHaveLearnedWordsQueue());
        reviewCardViewModel.saveWordCardState(wordCardState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        reviewCardViewModel.initFromDataBase(DateUtils.getDate());
        super.onActivityCreated(savedInstanceState);
    }
}
