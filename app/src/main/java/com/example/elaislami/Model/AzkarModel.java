package com.example.elaislami.Model;

import com.google.gson.annotations.SerializedName;

/*
 * This model is used to represent Azkar
 */
public class AzkarModel {

    @SerializedName("count")
    private final String count;

    @SerializedName("content")
    private String content;

    // Here is the constructor
    public AzkarModel(String count, String content) {
        this.count = count;
        this.content = content;
    }

    /*
     * Setters and getters
     */
    public String getCount() {
        return count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
