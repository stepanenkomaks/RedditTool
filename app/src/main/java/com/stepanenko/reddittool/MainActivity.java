package com.stepanenko.reddittool;

import static com.stepanenko.reddittool.services.GetTopPostsService.getResponse;
import static com.stepanenko.reddittool.util.ParseJson.parseJson;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepanenko.reddittool.models.RedditPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final int DEFAULT_LIMIT = 10;
    private ProgressBar loadingIndicator;

    private NestedScrollView nestedScrollView;

    private List<RedditPost> posts;

    private RecyclerView recyclerView;

    private String afterParameter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nestedScrollView = findViewById(R.id.scroll_view);
        loadingIndicator = findViewById(R.id.loading_indicator);
        recyclerView = findViewById(R.id.list_of_posts);
        posts = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        this.getData(afterParameter);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        afterParameter = posts.get(posts.size() - 1).getName();
                        loadingIndicator.setVisibility(View.VISIBLE);
                        getData(afterParameter);
                    }
                });
    }

    private void showErrorToast() {
        Toast.makeText(this, "Error( Try again!", Toast.LENGTH_LONG).show();
    }

    private void getData(String after) {
        loadingIndicator.setVisibility(View.VISIBLE);
        Call call = getResponse(DEFAULT_LIMIT, after);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> showErrorToast());
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
                } else {
                    runOnUiThread(() -> showErrorToast());
                }
            }
        });

        loadingIndicator.setVisibility(View.INVISIBLE);
    }
}