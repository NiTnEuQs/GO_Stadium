package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksFixture extends Links {

    @SerializedName("competition")
    @Expose
    private Href competition;

    @SerializedName("homeTeam")
    @Expose
    private Href homeTeam;

    @SerializedName("awayTeam")
    @Expose
    private Href awayTeam;

    // Getters / Setters

    public Href getCompetition() {
        return competition;
    }

    public void setCompetition(Href competition) {
        this.competition = competition;
    }

    public Href getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Href homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Href getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Href awayTeam) {
        this.awayTeam = awayTeam;
    }
}
