package com.example.sourcewords.ui.review.model;

import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.sourcewords.App;
import com.example.sourcewords.commonUtils.NetUtil;
import com.example.sourcewords.ui.mine.model.databean.UserInfoRemoteDataSource;
import com.example.sourcewords.ui.mine.model.databean.UserWrapper;
import com.example.sourcewords.ui.review.dataBean.SingleWord;
import com.example.sourcewords.ui.review.dataBean.Word;
import com.example.sourcewords.ui.review.dataBean.WordRoot;
import com.example.sourcewords.utils.Converters;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    public static List<WordRoot> getRoots() throws IOException {
        AssetManager assetManager = App.getAppContext().getAssets();
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = assetManager.open("WordRootData.json");
        String json = convertStreamToString(inputStream);
        return Converters.WordRootJson2Class(json);
    }

    public static List<SingleWord> getSingleWords() throws IOException {
        AssetManager assetManager = App.getAppContext().getAssets();
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = assetManager.open("WordData.json");
        String json = convertStreamToString(inputStream);
        return Converters.SingleWordJson2Class(json);
    }

    public static String convertStreamToString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
