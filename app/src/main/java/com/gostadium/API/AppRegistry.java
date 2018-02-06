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

public class AppRegistry {

    public static Retrofit retrofit = RetrofitClient.getClient(SoccerDataService.ENDPOINT);
    public static SoccerDataService service = retrofit.create(SoccerDataService.class);

    public static Competition competition;
    public static List<Fixture> competition_fixtures;

    public synchronized static void updateCompetition(final Updatable updatable, int competitionid) {
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

                if (updatable != null) updatable.update();
            }

            @Override
            public void onFailure(@NonNull Call<Competition> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    public static void updateCompetitionFixtures(final Updatable updatable, final int competitionid) {
        competition_fixtures = null;

        service.getCompetitionFixtures(competitionid).enqueue(new Callback<Fixtures>() {
            @Override
            public void onResponse(@NonNull Call<Fixtures> call, @NonNull Response<Fixtures> response) {
                if (response.isSuccessful()) {
                    Fixtures res = response.body();
                    if (res != null) competition_fixtures = res.getFixtures();
                } else {
                    competition_fixtures = new ArrayList<>();
                    Log.w("AppRegistry", "Response is not successful. Code " + response.code());
                }

                if (updatable != null) updatable.update();
            }

            @Override
            public void onFailure(@NonNull Call<Fixtures> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });
    }

    Object returnData(Object data) {
        return data;
    }

}
