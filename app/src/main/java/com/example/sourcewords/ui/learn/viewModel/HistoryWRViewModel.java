package com.example.sourcewords.ui.learn.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sourcewords.ui.learn.dataBean.HistoryWordRoot;
import com.example.sourcewords.ui.learn.model.HistoryWRRepository;
import com.example.sourcewords.ui.learn.model.WordRootDBCallBack;
import com.example.sourcewords.ui.learn.model.WordRootRepository;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.model.HistoryDBCallback;

import java.util.List;

public class HistoryWRViewModel extends AndroidViewModel {
    private HistoryWRRepository mHistoryWRRepository;
    private WordRootRepository mWordRootRepository;
    public HistoryWRViewModel(@NonNull Application application) {
        super(application);
        mHistoryWRRepository = new HistoryWRRepository();
        mWordRootRepository = new WordRootRepository();
    }

    public void Insert(HistoryWordRoot historyWordsRoot, HistoryDBCallback HistoryDBCallback){mHistoryWRRepository.Insert(historyWordsRoot, HistoryDBCallback);}

    public void Delete(HistoryWordRoot historyWordsRoot, HistoryDBCallback HistoryDBCallback){mHistoryWRRepository.Delete(historyWordsRoot,HistoryDBCallback);}

    public void Clear(HistoryDBCallback HistoryDBCallback){mHistoryWRRepository.Clear(HistoryDBCallback);}

    public List<HistoryWordRoot> getList(){return mHistoryWRRepository.getList();}
}
