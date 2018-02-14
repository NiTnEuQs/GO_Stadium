package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeagueTable {

    @SerializedName("_links")
    @Expose
    private LinksBase _links;

    @SerializedName("leagueCaption")
    @Expose
    private String leagueCaption;

    @SerializedName("matchday")
    @Expose
    private int matchday;

    @SerializedName("standing")
    @Expose
    private List<Standing> standing;

    // Getters / Setters

    public LinksBase get_links() {
        return _links;
    }

    public void set_links(LinksBase _links) {
        this._links = _links;
    }

    public String getLeagueCaption() {
        return leagueCaption;
    }

    public void setLeagueCaption(String leagueCaption) {
        this.leagueCaption = leagueCaption;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    public List<Standing> getStanding() {
        return standing;
    }

    public void setStanding(List<Standing> standing) {
        this.standing = standing;
    }
}
