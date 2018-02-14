package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Teams {

    @SerializedName("_links")
    @Expose
    private LinksBase _links;

    @SerializedName("teams")
    @Expose
    private List<Team> teams;

    // Getters / Setters

    List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

}
