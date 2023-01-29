package com.stepanenko.reddittool.util;

import static com.stepanenko.reddittool.util.ConverterForDate.convert;

import android.util.NoSuchPropertyException;

import com.stepanenko.reddittool.models.RedditPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJson {
    public static RedditPost parseJson(String response) throws JSONException{
        if (response != null && !response.equals("")) {
            //Go to JSONObject with required data
            String data = new JSONObject(response).getString("data");
            String children = new JSONObject(data).getString("children");
            String childrenData = new JSONArray(children).getJSONObject(0)
                    .getString("data");
            JSONObject childrenDataJSON = new JSONObject(childrenData);

            //Get data from JSONObject
            String username = childrenDataJSON.getString("subreddit");
            String thumbnailUrl =  childrenDataJSON.getString("thumbnail");
            Long createdAt = childrenDataJSON.getLong("created");
            int numComments = childrenDataJSON.getInt("num_comments");
            String title = childrenDataJSON.getString("title");

            //Set data to our model
            RedditPost redditPost = new RedditPost();
            redditPost.setUsername(username);
            redditPost.setThumbnail(thumbnailUrl);
            redditPost.setTitle(title);
            redditPost.setComments(numComments);
            redditPost.setCreatedAt(convert(createdAt));

            return redditPost;
        } else {
            throw new NoSuchPropertyException("There is no response");
        }
    }
}
