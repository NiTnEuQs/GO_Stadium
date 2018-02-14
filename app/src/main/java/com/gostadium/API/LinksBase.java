package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksBase extends Links {

    @SerializedName("competition")
    @Expose
    private Href competition;

    // Getters / Setters

    public Href getCompetition() {
        return competition;
    }

    public void setCompetition(Href competition) {
        this.competition = competition;
    }
}
