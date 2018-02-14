package com.gostadium.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gostadium.API.AppRegistry;
import com.gostadium.API.Fixture;
import com.gostadium.API.Interfaces.Updatable;
import com.gostadium.Adapters.MatchAdapter;
import com.gostadium.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Classe du fragment de news (présent dans un onglet de l'écran principal)
 */
public class NewsFragment extends Fragment {

    // Code de la compétition (Ligue 1). Temporaire.
    final int leagueId = 450;

    /**
     * Variables des recyclerview présents sur l'écran
     */
    RecyclerView rv_results;
    RecyclerView rv_incomingMatchs;
    RecyclerView rv_pendingMatchs;

    /**
     * Variables des adapters pour les recyclerview
     */
    MatchAdapter adapter_results;
    MatchAdapter adapter_incomingMatchs;
    MatchAdapter adapter_pendingMatchs;

    /**
     * Variables des listes utilisées par les adapters
     */
    List<Fixture> results;
    List<Fixture> incomingMatchs;
    List<Fixture> pendingMatchs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

        initialize(rootView);

        return rootView;
    }

    /**
     * Initialise la vue et met à jour le registre avec les données de l'API
     * @param view La vue actuelle
     */
    void initialize (View view) {
        // Initialise les variables du layout
        rv_results = view.findViewById(R.id.rv_last_result);
        rv_results.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_incomingMatchs = view.findViewById(R.id.rv_incomingMatchs);
        rv_incomingMatchs.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_pendingMatchs = view.findViewById(R.id.rv_pendingMatchs);
        rv_pendingMatchs.setLayoutManager(new LinearLayoutManager(getContext()));

        /*
          Met à jour les compétitions avec les données de l'API grâce au code de la
          compétition leagueId.
          Une fois la requête terminée, on est redirigés vers la méthode getCompetitionUpdatable
        */
        AppRegistry.updateCompetition(getCompetitionUpdatable(), leagueId);
    }

    /**
     * Une fois la requête terminée, on est redirigés vers cette méthode et avons accès
     * à toutes les compétitions, ce qui permet de mettre à jour les informations à l'écran
     * @return Un updatable qui contient ce que l'on veut faire
     * @see Updatable
     */
    Updatable getCompetitionUpdatable () {
        return new Updatable() {
            @Override
            public void update(Response response) {
                /*
                  Met à jour les matchs d'une compétition avec les données de l'API grâce au code
                  de la compétition leagueId.
                  Une fois la requête terminée, on est redirigés vers la méthode
                  getCompetitionFixturesUpdatable
                */
                AppRegistry.updateCompetitionFixtures(getCompetitionFixturesUpdatable(), leagueId);
            }
        };
    }

    /**
     * Une fois la requête terminée, on est redirigés vers cette méthode et avons accès
     * à tous les matchs d'une compétition, ce qui permet de mettre à jour les informations
     * à l'écran
     * @return Un updatable qui contient ce que l'on veut faire
     * @see Updatable
     */
    Updatable getCompetitionFixturesUpdatable () {
        return new Updatable() {
            @Override
            public void update(Response response) {
                // On initialise les variables des listes
                results = new ArrayList<>();
                incomingMatchs = new ArrayList<>();
                pendingMatchs = new ArrayList<>();

                /*
                  On met à jour les listes avec les données du registre
                  @see AppRegistry
                */
                for (Fixture fixture : AppRegistry.fixtures) {
                    if (fixture.getStatus().equals("FINISHED") && fixture.getMatchday() == AppRegistry.competition.getCurrentMatchday() - 1) {
                        results.add(fixture);
                    }
                }

                for (Fixture fixture : AppRegistry.fixtures) {
                    if (fixture.getMatchday() == AppRegistry.competition.getCurrentMatchday() + 1) {
                        incomingMatchs.add(fixture);
                    }
                }

                for (Fixture fixture : AppRegistry.fixtures) {
                    if (fixture.getStatus().equals("IN_PLAY")) {
                        pendingMatchs.add(fixture);
                    }
                }

                /*
                  On initialise les adapters pour les recyclerview
                  @see MatchAdapter
                */
                adapter_results = new MatchAdapter(results);
                adapter_incomingMatchs = new MatchAdapter(incomingMatchs);
                adapter_pendingMatchs = new MatchAdapter(pendingMatchs);

                // On met à jour les adapters pour afficher les résultats
                rv_results.setAdapter(adapter_results);
                rv_incomingMatchs.setAdapter(adapter_incomingMatchs);
                rv_pendingMatchs.setAdapter(adapter_pendingMatchs);
            }
        };
    }

}
