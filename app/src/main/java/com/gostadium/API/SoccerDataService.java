package com.gostadium.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SoccerDataService {

    String ENDPOINT = "http://api.football-data.org/v1/";

    @GET("teams")
    Call<List<Team>> getTeams();

    @GET("teams/{teamid}")
    Call<Team> getTeam(@Path("teamid") int teamid);

    @GET("teams/{teamid}/fixtures")
    Call<Fixtures> getTeamFixtures(@Path("teamid") int teamid);

    @GET("teams/{teamid}/players")
    Call<List<Players>> getTeamPlayers(@Path("teamid") int teamid);

    @GET("competitions")
    Call<List<Competition>> getCompetitions();

    @GET("competitions/{competitionid}")
    Call<Competition> getCompetition(@Path("competitionid") int competitionid);

    @GET("competitions/{competitionid}/teams")
    Call<List<Team>> getCompetitionTeams(@Path("competitionid") int competitionid);

    @GET("competitions/{competitionid}/fixtures")
    Call<Fixtures> getCompetitionFixtures(@Path("competitionid") int competitionid);

    /*
    @GET("/search/repositories")
    Call<List<Team>> searchRepos(@Query("q") String query);
    */

}
