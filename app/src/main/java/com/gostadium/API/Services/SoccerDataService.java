package com.gostadium.API.Services;

import com.gostadium.API.Competition;
import com.gostadium.API.Fixtures;
import com.gostadium.API.Players;
import com.gostadium.API.Team;
import com.gostadium.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface SoccerDataService {

    String ENDPOINT = "http://api.football-data.org/v1/";
    String API_KEY = "b253f28107cb4cd1b058b6f2e6e0853a";

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("teams")
    Call<List<Team>> getTeams();

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("teams/{teamid}")
    Call<Team> getTeam(@Path("teamid") int teamid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("teams/{teamid}/fixtures")
    Call<Fixtures> getTeamFixtures(@Path("teamid") int teamid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("teams/{teamid}/players")
    Call<List<Players>> getTeamPlayers(@Path("teamid") int teamid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions")
    Call<List<Competition>> getCompetitions();

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions/{competitionid}")
    Call<Competition> getCompetition(@Path("competitionid") int competitionid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions/{competitionid}/teams")
    Call<List<Team>> getCompetitionTeams(@Path("competitionid") int competitionid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions/{competitionid}/fixtures")
    Call<Fixtures> getCompetitionFixtures(@Path("competitionid") int competitionid);

    /*
    @GET("/search/repositories")
    Call<List<Team>> searchRepos(@Query("q") String query);
    */

}
