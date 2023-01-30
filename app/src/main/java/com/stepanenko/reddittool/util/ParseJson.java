package com.stepanenko.reddittool.util;

import static com.stepanenko.reddittool.util.ConverterForDate.convert;

import android.util.NoSuchPropertyException;

import com.stepanenko.reddittool.models.RedditPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJson {
    public static List<RedditPost> parseJson(JSONObject response) throws JSONException{
        if (response != null) {
            List<RedditPost> posts = new ArrayList<>();

            //Go to JSONObject with required data
            JSONArray childrenDataArr = response.getJSONObject("data")
                    .optJSONArray("children");

            for (int i = 0; i < childrenDataArr.length(); i++) {
                String childrenData = childrenDataArr.getJSONObject(i)
                        .getString("data");
                JSONObject childrenDataJSON = new JSONObject(childrenData);

                //Get data from JSONObject
                String name = childrenDataJSON.getString("name");
                String username = childrenDataJSON.getString("subreddit");
                String thumbnailUrl =  childrenDataJSON.getString("thumbnail");
                Long createdAt = childrenDataJSON.getLong("created");
                int numComments = childrenDataJSON.getInt("num_comments");
                String title = childrenDataJSON.getString("title");

                //Set data to our model
                RedditPost redditPost = new RedditPost();
                redditPost.setName(name);
                redditPost.setAuthor(username);
                redditPost.setThumbnail(thumbnailUrl);
                redditPost.setTitle(title);
                redditPost.setComments(numComments);
                redditPost.setCreatedAt(convert(createdAt));

                posts.add(redditPost);
            }

            return posts;
        } else {
            throw new NoSuchPropertyException("There is no response");
        }
    }
}
