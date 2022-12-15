package com.example.elaislami.Model;

import com.google.gson.annotations.SerializedName;

public class AzkarModel {

    @SerializedName("count")
    private String count;

    @SerializedName("content")
    private String content;


    public AzkarModel(String count, String content) {
        this.count = count;
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
