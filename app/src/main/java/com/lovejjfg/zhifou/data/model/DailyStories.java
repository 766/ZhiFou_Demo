package com.lovejjfg.zhifou.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张俊 on 2016/2/21.
 */
public class DailyStories {
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("stories")
    private List<Story> stories;
    @Expose
    @SerializedName("top_stories")
    private List<Story> topStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<Story> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<Story> topStories) {
        this.topStories = topStories;
    }
}
