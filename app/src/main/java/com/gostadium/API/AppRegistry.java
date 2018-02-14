package com.gostadium.API;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gostadium.API.Client.RetrofitClient;
import com.gostadium.API.Interfaces.Updatable;
import com.gostadium.API.Services.SoccerDataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * La classe qui contient le registre de l'application.
 * Les données de l'API sont stockés dans ces variables le temps de l'utilisation de l'application
 */
public class AppRegistry {

    // Variables du WebService
    private static Retrofit retrofit = RetrofitClient.getClient(SoccerDataService.ENDPOINT);
    private static SoccerDataService service = retrofit.create(SoccerDataService.class);

    // Variables des différentes données

    public static Competition competition;
    public static List<Fixture> fixtures;
    public static List<Team> teams;
    public static List<Fixture> team_fixtures;

    public static List<Competition> competitions;
    public static List<List<Fixture>> competition_fixtures = new ArrayList<>();
    public static List<List<Team>> competition_teams = new ArrayList<>();
    public static List<LeagueTable> competition_leagueTable = new ArrayList<>();

    /**
     * Fait une requête sur la compétition et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     * @param competitionid L'index de la competition
     */
    public static void updateCompetition(final Updatable updatable, int competitionid) {
        competition = null;

        service.getCompetition(competitionid).enqueue(new Callback<Competition>() {
            @Override
            public void onResponse(@NonNull Call<Competition> call, @NonNull Response<Competition> response) {
                if (response.isSuccessful()) {
                    Competition res = response.body();
                    if (res != null) competition = res;
                } else {
                    competition = new Competition();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<Competition> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    /**
     * Fait une requête sur les équipes d'une compétition et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     * @param competitionid L'index de la competition
     */
    public static void updateCompetitionTeams(final Updatable updatable, final int competitionid) {
        teams = null;

        service.getCompetitionTeams(competitionid).enqueue(new Callback<Teams>() {
            @Override
            public void onResponse(@NonNull Call<Teams> call, @NonNull Response<Teams> response) {
                if (response.isSuccessful()) {
                    Teams body = response.body();
                    assert body != null;
                    List<Team> res = body.getTeams();
                    if (res != null) {
                        teams = res;
                        competition_teams.add(teams);
                    }
                } else {
                    teams = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<Teams> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    /**
     * Fait une requête sur les matchs de la compétition et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     * @param competitionid L'index de la competition
     */
    public static void updateCompetitionFixtures(final Updatable updatable, final int competitionid) {
        fixtures = null;

        service.getCompetitionFixtures(competitionid).enqueue(new Callback<Fixtures>() {
            @Override
            public void onResponse(@NonNull Call<Fixtures> call, @NonNull Response<Fixtures> response) {
                if (response.isSuccessful()) {
                    Fixtures res = response.body();
                    if (res != null) {
                        fixtures = res.getFixtures();
                        competition_fixtures.add(fixtures);
                    }
                } else {
                    fixtures = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<Fixtures> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    /**
     * Fait une requête sur les matchs de l'équipe et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     * @param teamid L'index de l'équipe
     */
    public static void updateTeamFixtures(final Updatable updatable, final int teamid) {
        team_fixtures = null;

        service.getTeamFixtures(teamid).enqueue(new Callback<Fixtures>() {
            @Override
            public void onResponse(@NonNull Call<Fixtures> call, @NonNull Response<Fixtures> response) {
                if (response.isSuccessful()) {
                    Fixtures res = response.body();
                    if (res != null) team_fixtures = res.getFixtures();
                } else {
                    team_fixtures = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<Fixtures> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Fait une requête sur toutes compétition et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     */
    public static void updateCompetitions(final Updatable updatable) {
        service.getCompetitions().enqueue(new Callback<List<Competition>>() {
            @Override
            public void onResponse(@NonNull Call<List<Competition>> call, @NonNull Response<List<Competition>> response) {
                if (response.isSuccessful()) {
                    List<Competition> res = response.body();
                    if (res != null) competitions = res;
                } else {
                    competitions = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<Competition>> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    /**
     * Fait une requête sur des équipes et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     * @param url URL de la requête (requête dynamique)
     */
    public static void updateTeams(final Updatable updatable, String url) {
        service.getDynamicTeams(url).enqueue(new Callback<Teams>() {
            @Override
            public void onResponse(@NonNull Call<Teams> call, @NonNull Response<Teams> response) {
                if (response.isSuccessful()) {
                    Teams res = response.body();
                    if (res != null) competition_teams.add(res.getTeams());
                } else {
                    competition_teams = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<Teams> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    /**
     * Fait une requête sur des matchs et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     * @param url URL de la requête (requête dynamique)
     */
    public static void updateFixtures(final Updatable updatable, String url) {
        service.getDynamicFixtures(url).enqueue(new Callback<Fixtures>() {
            @Override
            public void onResponse(@NonNull Call<Fixtures> call, @NonNull Response<Fixtures> response) {
                if (response.isSuccessful()) {
                    Fixtures res = response.body();
                    if (res != null) competition_fixtures.add(res.getFixtures());
                } else {
                    competition_fixtures = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<Fixtures> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    /**
     * Fait une requête sur un classement et stocke les données dans une variable
     * @param updatable Classe permettant de gérer les données une fois la requête terminée
     * @param url URL de la requête (requête dynamique)
     */
    public static void updateLeagueTable(final Updatable updatable, String url) {
        service.getDynamicLeagueTable(url).enqueue(new Callback<LeagueTable>() {
            @Override
            public void onResponse(@NonNull Call<LeagueTable> call, @NonNull Response<LeagueTable> response) {
                if (response.isSuccessful()) {
                    LeagueTable res = response.body();
                    if (res != null) competition_leagueTable.add(res);
                } else {
                    competition_leagueTable = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update(response);
            }

            @Override
            public void onFailure(@NonNull Call<LeagueTable> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

}
