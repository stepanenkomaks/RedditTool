package com.stepanenko.reddittool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stepanenko.reddittool.models.RedditPost;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private final List<RedditPost> posts;
    private final Activity activity;

    public MainAdapter(List<RedditPost> posts, Activity activity) {
        this.posts = posts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForRecycleViewItems = R.layout.post_info;

        View view = LayoutInflater.from(context)
                .inflate(idForRecycleViewItems, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RedditPost post = posts.get(position);

        holder.author.setText(post.getAuthor());
        holder.title.setText(post.getTitle());
        holder.creationTime.setText(post.getCreatedAt());
        holder.comments.setText(post.getComments() + " comments");

        String thumbnail = post.getThumbnail();
        if (!thumbnail.equals("self")) {
            Glide.with(activity).load(thumbnail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumbnail);

            holder.thumbnail.setOnClickListener(view -> {
                Intent i = new Intent(view.getContext(), FullSizeActivity.class);
                i.putExtra("url", post.getThumbnail());
                view.getContext().startActivity(i);
            });
        } else {
            holder.thumbnail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView author;
        TextView title;
        TextView comments;
        TextView creationTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            comments = itemView.findViewById(R.id.comments);
            creationTime = itemView.findViewById(R.id.time_of_creation);
        }
    }
}
