package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("self")
    @Expose
    private Href self;

    // Getters / Setters

    public Href getSelf() {
        return self;
    }

    public void setSelf(Href self) {
        this.self = self;
    }
}
