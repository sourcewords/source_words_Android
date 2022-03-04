package com.example.sourcewords.ui.review.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sourcewords.ui.review.dataBean.HistoryWord;
import com.example.sourcewords.ui.review.model.HistoryRepository;
import com.example.sourcewords.ui.review.model.HistoryDBCallback;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository mHistoryRepository;
    public HistoryViewModel(@NonNull Application application) {
        super(application);
        mHistoryRepository = new HistoryRepository();
    }

    public void Insert(HistoryWord historyWords, HistoryDBCallback HistoryDBCallback){mHistoryRepository.Insert(historyWords, HistoryDBCallback);}

    public void Delete(HistoryWord... historyWords){mHistoryRepository.Delete(historyWords);}

    public void Clear(HistoryDBCallback HistoryDBCallback){mHistoryRepository.Clear(HistoryDBCallback);}

    public List<HistoryWord> getList(){return mHistoryRepository.getList();}
}
