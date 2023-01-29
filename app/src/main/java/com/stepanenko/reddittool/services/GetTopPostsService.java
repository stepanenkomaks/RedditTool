package com.stepanenko.reddittool.services;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetTopPostsService {
    private static final OkHttpClient client = new OkHttpClient();

    public static Call getResponse(int limit){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("www.reddit.com")
                .addPathSegment("top.json")
                .addQueryParameter("limit", String.valueOf(limit))
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        return client.newCall(request);
    }
}
