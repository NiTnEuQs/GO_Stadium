package com.gostadium.API.Services;

import com.gostadium.API.Competition;
import com.gostadium.API.Fixtures;
import com.gostadium.API.LeagueTable;
import com.gostadium.API.Players;
import com.gostadium.API.Team;
import com.gostadium.API.Teams;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * La classe qui gère les requêtes faites auprès de l'API
 */
public interface SoccerDataService {

    // URL de début de l'API
    String ENDPOINT = "http://api.football-data.org/v1/";
    // Clé de l'API (permet d'avoir des avantages, 50 requêtes/min)
    String API_KEY = "b253f28107cb4cd1b058b6f2e6e0853a";

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions")
    Call<List<Competition>> getCompetitions();

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions/{competitionid}")
    Call<Competition> getCompetition(@Path("competitionid") int competitionid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions/{competitionid}/teams")
    Call<Teams> getCompetitionTeams(@Path("competitionid") int competitionid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions/{competitionid}/leagueTable")
    Call<LeagueTable> getCompetitionTable(@Path("competitionid") int competitionid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("competitions/{competitionid}/fixtures")
    Call<Fixtures> getCompetitionFixtures(@Path("competitionid") int competitionid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("fixtures")
    Call<Fixtures> getFixtures();

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("fixtures/{id}")
    Call<Fixtures> getFixtures(@Path("id") int id);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("teams/{teamid}")
    Call<Team> getTeam(@Path("teamid") int teamid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("teams/{teamid}/fixtures")
    Call<Fixtures> getTeamFixtures(@Path("teamid") int teamid);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET("teams/{teamid}/players")
    Call<List<Players>> getTeamPlayers(@Path("teamid") int teamid);

    // Dynamiques

    @Headers("X-Auth-Token: " + API_KEY)
    @GET
    Call<Competition> getDynamicCompetition(@Url String url);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET
    Call<Teams> getDynamicTeams(@Url String url);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET
    Call<Fixtures> getDynamicFixtures(@Url String url);

    @Headers("X-Auth-Token: " + API_KEY)
    @GET
    Call<LeagueTable> getDynamicLeagueTable(@Url String url);

    /*
    @GET("/search/repositories")
    Call<List<Team>> searchRepos(@Query("q") String query);
    */

}
