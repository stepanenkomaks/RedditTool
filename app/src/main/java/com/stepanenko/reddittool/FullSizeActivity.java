package com.stepanenko.reddittool;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class FullSizeActivity extends AppCompatActivity {
    private ImageView imageView;
    private String url;
    private final String FILE_NAME = "Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size);

        imageView = findViewById(R.id.image);
        Intent i = getIntent();
        url = i.getStringExtra("url");

        Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        imageView.setOnClickListener(view -> downloadImage());
    }

    private void downloadImage(){
        try{
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(FILE_NAME)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator+FILE_NAME+".jpg");

            downloadManager.enqueue(request);

            Toast.makeText(this, "Image downloaded successfully", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(this, "Image download fail", Toast.LENGTH_LONG).show();
        }
    }
}
