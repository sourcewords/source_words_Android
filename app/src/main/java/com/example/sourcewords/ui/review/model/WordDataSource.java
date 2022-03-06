package com.example.sourcewords.ui.review.model;

import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.sourcewords.App;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.utils.Converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;


public class WordDataSource {
    private static WordDataSource INSTANCE;

    private final String token = UserWrapper.getInstance().getToken();

    public static WordDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (WordDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WordDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private WordDataSource() {
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<WordRoot> getRoots() throws IOException {
        AssetManager assetManager = App.getAppContext().getAssets();
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = assetManager.open("WordRootData.json");
        String json = convertStreamToString(inputStream);
        return Converters.WordRootJson2Class(json);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<SingleWord> getSingleWords() throws IOException {
        AssetManager assetManager = App.getAppContext().getAssets();
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = assetManager.open("WordData.json");
        String json = convertStreamToString(inputStream);
        return Converters.SingleWordJson2Class(json);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String convertStreamToString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
