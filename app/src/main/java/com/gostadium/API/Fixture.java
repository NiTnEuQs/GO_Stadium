package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fixture {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("matchday")
    @Expose
    private int matchday;

    @SerializedName("homeTeamName")
    @Expose
    private String homeTeamName;

    @SerializedName("awayTeamName")
    @Expose
    private String awayTeamName;

    @SerializedName("result")
    @Expose
    private Result result;

    /*
    @SerializedName("odds")
    @Expose
    private int odds;
    */

    // Getters / Setters

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    /*
    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
        this.odds = odds;
    }
    */

}
