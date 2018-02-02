package com.gostadium.API;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppRegistry {

    private static Retrofit retrofit = RetrofitClient.getClient(SoccerDataService.ENDPOINT);
    private static SoccerDataService service = retrofit.create(SoccerDataService.class);

    public static List<Fixture> competition_fixtures;
    public static List<Fixture> updateCompetitionFixtures(int competitionid) {
        service.getCompetitionFixtures(competitionid).enqueue(new Callback<Fixtures>() {
            @Override
            public void onResponse(Call<Fixtures> call, Response<Fixtures> response) {
                competition_fixtures = response.body().getFixtures();
            }

            @Override
            public void onFailure(Call<Fixtures> call, Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });

        return competition_fixtures;
    }

    public static Competition competition;
    public static Competition updateCompetition(int competitionid) {
        service.getCompetition(competitionid).enqueue(new Callback<Competition>() {
            @Override
            public void onResponse(Call<Competition> call, Response<Competition> response) {
                competition = response.body();
            }

            @Override
            public void onFailure(Call<Competition> call, Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });

        return competition;
    }

}
