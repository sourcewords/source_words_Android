package com.example.sourcewords.ui.learn;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.ui.review.dataBean.WordRootDao;
import com.example.sourcewords.ui.review.db.WordDatabase;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordRootRepository {
    private WordRootDao wordRootDao;
    private LiveData<List<WordRoot>> wordRootLiveData;
    private Context context;
    private static Retrofit retrofit;


    public WordRootRepository(Context mContext){
        final WordDatabase wordDatabase = WordDatabase.getDatabase(mContext);
        wordRootDao = wordDatabase.getWordDao();
        context = mContext;
    }

    public LiveData<List<WordRoot>> getAllWordRoots(){
        wordRootLiveData = wordRootDao.getAllWordRoot();
        return wordRootLiveData;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null){
            synchronized(WordRootRepository.class){
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://112.126.76.187:9999/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }
}
