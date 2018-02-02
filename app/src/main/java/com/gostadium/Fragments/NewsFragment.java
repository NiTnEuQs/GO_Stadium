package com.gostadium.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gostadium.API.AppRegistry;
import com.gostadium.API.Fixture;
import com.gostadium.API.Fixtures;
import com.gostadium.API.RetrofitClient;
import com.gostadium.API.SoccerDataService;
import com.gostadium.Adapters.MyAdapter;
import com.gostadium.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsFragment extends Fragment {

    private Retrofit retrofit = RetrofitClient.getClient(SoccerDataService.ENDPOINT);
    private SoccerDataService service = retrofit.create(SoccerDataService.class);

    RecyclerView rv_results;
    ArrayList<Fixture> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        AppRegistry.updateCompetition(450);
        AppRegistry.updateCompetitionFixtures(450);

        rv_results = rootView.findViewById(R.id.rv_results);

        service.getCompetitionFixtures(450).enqueue(new Callback<Fixtures>() {
            @Override
            public void onResponse(Call<Fixtures> call, Response<Fixtures> response) {
                Fixtures fixtures = response.body();
                results = new ArrayList<>(fixtures.getFixtures().size());

                for (Fixture fixture : fixtures.getFixtures()) {
                    if (fixture.getStatus().equals("FINISHED") && fixture.getMatchday() == 23)
                        results.add(fixture);
                }

                rv_results.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_results.setAdapter(new MyAdapter(results));

                //adapter1.notifyItemInserted(fixturesStr.size() - 1);
            }

            @Override
            public void onFailure(Call<Fixtures> call, Throwable t) {
                Log.e("Retrofit", "Failure ... " + call.request().url() + "\n" + t.getCause());
            }
        });

        return rootView;
    }

}
