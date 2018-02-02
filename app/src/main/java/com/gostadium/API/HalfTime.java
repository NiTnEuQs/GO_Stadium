package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HalfTime {

    @SerializedName("goalsHomeTeam")
    @Expose
    private int goalsHomeTeam;

    @SerializedName("goalsAwayTeam")
    @Expose
    private int goalsAwayTeam;

    // Getters / Setters

    public int getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(int goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public int getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(int goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }

}
