package com.stepanenko.reddittool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class FullSizeActivity extends AppCompatActivity {
    ImageView imageView;
    String urlImage;
    String fileName = "Top_post_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size);

        imageView = findViewById(R.id.image);
        Intent i = getIntent();
        urlImage = i.getStringExtra("urlImage");

        Glide.with(this).load(urlImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
