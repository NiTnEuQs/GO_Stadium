package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Href {

    @SerializedName("href")
    @Expose
    private String href;

    // Getters / Setters

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
