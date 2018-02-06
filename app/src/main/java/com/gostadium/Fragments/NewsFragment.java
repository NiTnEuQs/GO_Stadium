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
import com.gostadium.API.Interfaces.Updatable;
import com.gostadium.Adapters.MyAdapter;
import com.gostadium.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    final int leagueId = 450;

    MyAdapter adapter_results;
    MyAdapter adapter_incomingMatchs;
    MyAdapter adapter_pendingMatchs;

    RecyclerView rv_results;
    RecyclerView rv_incomingMatchs;
    RecyclerView rv_pendingMatchs;

    List<Fixture> results;
    List<Fixture> incomingMatchs;
    List<Fixture> pendingMatchs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

        initialize(rootView);

        return rootView;
    }

    void initialize (View view) {
        rv_results = view.findViewById(R.id.rv_results);
        rv_results.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_incomingMatchs = view.findViewById(R.id.rv_incomingMatchs);
        rv_incomingMatchs.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_pendingMatchs = view.findViewById(R.id.rv_pendingMatchs);
        rv_pendingMatchs.setLayoutManager(new LinearLayoutManager(getContext()));

        AppRegistry.updateCompetition(getCompetitionUpdatable(), leagueId);
    }

    Updatable getCompetitionUpdatable () {
        return new Updatable() {
            @Override
            public void update() {
                AppRegistry.updateCompetitionFixtures(getCompetitionFixturesUpdatable(), leagueId);
            }
        };
    }

    Updatable getCompetitionFixturesUpdatable () {
        return new Updatable() {
            @Override
            public void update() {
                results = new ArrayList<>();
                incomingMatchs = new ArrayList<>();
                pendingMatchs = new ArrayList<>();

                for (Fixture fixture : AppRegistry.competition_fixtures) {
                    if (fixture.getStatus().equals("FINISHED") && fixture.getMatchday() == AppRegistry.competition.getCurrentMatchday()) {
                        results.add(fixture);
                    }
                }

                for (Fixture fixture : AppRegistry.competition_fixtures) {
                    if (fixture.getMatchday() == AppRegistry.competition.getCurrentMatchday() + 1) {
                        incomingMatchs.add(fixture);
                    }
                }

                for (Fixture fixture : AppRegistry.competition_fixtures) {
                    if (fixture.getStatus().equals("IN_PLAY")) {
                        pendingMatchs.add(fixture);
                    }
                }

                adapter_results = new MyAdapter(results);
                adapter_incomingMatchs = new MyAdapter(incomingMatchs);
                adapter_pendingMatchs = new MyAdapter(pendingMatchs);

                rv_results.setAdapter(adapter_results);
                rv_incomingMatchs.setAdapter(adapter_incomingMatchs);
                rv_pendingMatchs.setAdapter(adapter_pendingMatchs);
            }
        };
    }

}
