package com.stepanenko.reddittool.models;

public class RedditPost {
    private String author;

    private String thumbnail;

    private String createdAt;

    private String title;

    private int comments;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "username='" + author + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", title='" + title + '\'' +
                ", comments=" + comments +
                '}';
    }
}
