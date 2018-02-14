package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksCompetition extends Links {

    @SerializedName("teams")
    @Expose
    private Href teams;

    @SerializedName("fixtures")
    @Expose
    private Href fixtures;

    @SerializedName("leagueTable")
    @Expose
    private Href leagueTable;

    // Getters / Setters

    public Href getTeams() {
        return teams;
    }

    public void setTeams(Href teams) {
        this.teams = teams;
    }

    public Href getFixtures() {
        return fixtures;
    }

    public void setFixtures(Href fixtures) {
        this.fixtures = fixtures;
    }

    public Href getLeagueTable() {
        return leagueTable;
    }

    public void setLeagueTable(Href leagueTable) {
        this.leagueTable = leagueTable;
    }
}
