package com.stepanenko.reddittool;

import static com.stepanenko.reddittool.services.GetTopPostsService.getResponse;
import static com.stepanenko.reddittool.util.ParseJson.parseJson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.NoSuchPropertyException;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.stepanenko.reddittool.models.RedditPost;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final int DEFAULT_LIMIT = 1;

//    private TextView result;

    private TextView errorMessage;

    private ProgressBar loadingIndicator;

    private List<RedditPost> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        result = findViewById(R.id.show);
        errorMessage = findViewById(R.id.error_message);
        loadingIndicator = findViewById(R.id.loading_indicator);
        posts = new ArrayList<>();

        getData();
    }

    private void showResultTextView() {
//        result.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorTextView() {
        errorMessage.setVisibility(View.VISIBLE);
//        result.setVisibility(View.INVISIBLE);
    }

    private void getData() {
        loadingIndicator.setVisibility(View.VISIBLE);
        Call call = getResponse(DEFAULT_LIMIT);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                showErrorTextView();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    try {
                        RedditPost redditPost = parseJson(response.body().string());
                        runOnUiThread(() -> {
                            showResultTextView();
//                            result.setText(redditPost.toString());
                            posts.add(redditPost);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}