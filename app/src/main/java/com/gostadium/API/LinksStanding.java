package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksStanding {

    @SerializedName("team")
    @Expose
    private Href team;

    // Getters / Setters

    public Href getTeam() {
        return team;
    }

    public void setTeam(Href team) {
        this.team = team;
    }
}
