package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Standing {

    @SerializedName("_links")
    @Expose
    private LinksStanding _links;

    @SerializedName("position")
    @Expose
    private int position;

    @SerializedName("teamName")
    @Expose
    private String teamName;

    @SerializedName("playedGames")
    @Expose
    private int playedGames;

    @SerializedName("points")
    @Expose
    private int points;

    @SerializedName("goals")
    @Expose
    private int goals;

    @SerializedName("goalsAgainst")
    @Expose
    private int goalsAgainst;

    @SerializedName("goalDifference")
    @Expose
    private int goalDifference;

    @SerializedName("wins")
    @Expose
    private int wins;

    @SerializedName("draws")
    @Expose
    private int draws;

    @SerializedName("losses")
    @Expose
    private int losses;

    // Getters / Setters

    public LinksStanding get_links() {
        return _links;
    }

    public void set_links(LinksStanding _links) {
        this._links = _links;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
