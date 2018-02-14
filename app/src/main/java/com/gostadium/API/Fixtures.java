package com.gostadium.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fixtures {

    @SerializedName("_links")
    @Expose
    private LinksBase _links;

    @SerializedName("fixtures")
    @Expose
    private List<Fixture> fixtures;

    // Getters / Setters

    List<Fixture> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

}
