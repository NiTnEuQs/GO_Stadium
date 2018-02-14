package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksTeam extends Links {

    @SerializedName("fixtures")
    @Expose
    private Href fixtures;

    @SerializedName("players")
    @Expose
    private Href players;

    // Getters / Setters

    public Href getFixtures() {
        return fixtures;
    }

    public void setFixtures(Href fixtures) {
        this.fixtures = fixtures;
    }

    public Href getCompetition() {
        return players;
    }

    public void setCompetition(Href competition) {
        this.players = competition;
    }
}
