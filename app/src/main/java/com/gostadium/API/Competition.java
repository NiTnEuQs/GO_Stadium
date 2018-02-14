package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competition {

    @SerializedName("_links")
    @Expose
    private LinksCompetition _links;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("caption")
    @Expose
    private String caption;

    @SerializedName("league")
    @Expose
    private String league;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("currentMatchday")
    @Expose
    private int currentMatchday;

    @SerializedName("numberOfMatchdays")
    @Expose
    private int numberOfMatchdays;

    @SerializedName("numberOfTeams")
    @Expose
    private int numberOfTeams;

    @SerializedName("numberOfGames")
    @Expose
    private int numberOfGames;

    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    // Getters / Setters


    public LinksCompetition get_links() {
        return _links;
    }

    public void set_links(LinksCompetition _links) {
        this._links = _links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCurrentMatchday() {
        return currentMatchday;
    }

    public void setCurrentMatchday(int currentMatchday) {
        this.currentMatchday = currentMatchday;
    }

    public int getNumberOfMatchdays() {
        return numberOfMatchdays;
    }

    public void setNumberOfMatchdays(int numberOfMatchdays) {
        this.numberOfMatchdays = numberOfMatchdays;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
