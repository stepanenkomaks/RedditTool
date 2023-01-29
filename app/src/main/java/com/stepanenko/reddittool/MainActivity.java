package com.stepanenko.reddittool;

import static com.stepanenko.reddittool.services.GetTopPostsService.getResponse;
import static com.stepanenko.reddittool.util.ParseJson.parseJson;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepanenko.reddittool.models.RedditPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final int DEFAULT_LIMIT = 10;

    private TextView errorMessage;

    private ProgressBar loadingIndicator;

    private List<RedditPost> posts;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMessage = findViewById(R.id.error_message);
        loadingIndicator = findViewById(R.id.loading_indicator);
        recyclerView = findViewById(R.id.list_of_posts);
        posts = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        this.getData();
       // this.showData();
    }

    private void showErrorTextView() {
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void getData() {
        loadingIndicator.setVisibility(View.VISIBLE);
        Call call = getResponse(DEFAULT_LIMIT);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    showErrorTextView();
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful() && response.body() != null) {
                    runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            posts = parseJson(jsonObject);
                            MainAdapter adapter = new MainAdapter(posts, MainActivity.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    });

                }
            }
        });

        loadingIndicator.setVisibility(View.INVISIBLE);
    }

//    private void showData() {
//        MainAdapter adapter = new MainAdapter(posts);
//        recyclerView.setAdapter(adapter);
//        loadingIndicator.setVisibility(View.INVISIBLE);
//    }

}