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
import com.gostadium.API.Interfaces.Updatable;
import com.gostadium.API.Team;
import com.gostadium.Adapters.FavoritesAdapter;
import com.gostadium.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Classe du fragment des favoris (présent dans un onglet de l'écran principal)
 */
public class FavoritesFragment extends Fragment {

    // Code de la compétition (Ligue 1). Temporaire.
    final static int leagueId = 450;

    /**
     * Variables des recyclerview présents sur l'écran
     */
    RecyclerView rv_favorites;

    /**
     * Variables des adapters pour les recyclerview
     */
    FavoritesAdapter adapter_favorites;

    /**
     * Variables des listes utilisées par les adapters
     */
    public static List<String> favorites = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorites_fragment, container, false);

        initialize(rootView);

        return rootView;
    }

    /**
     * Initialise la vue et met à jour le registre avec les données de l'API
     * @param view La vue actuelle
     */
    void initialize (View view) {
        // Initialise les variables du layout
        rv_favorites = view.findViewById(R.id.rv_favorites);
        rv_favorites.setLayoutManager(new LinearLayoutManager(getContext()));

        /*
          Met à jour les compétitions avec les données de l'API grâce au code de la
          compétition leagueId.
          Une fois la requête terminée, on est redirigés vers la méthode getTeamsUpdatable
        */
        AppRegistry.updateCompetitionTeams(getTeamsUpdatable(), leagueId);
    }

    /**
     * Une fois la requête terminée, on est redirigés vers cette méthode et avons accès
     * à toutes les équipes, ce qui permet de mettre à jour les informations à l'écran
     * @return Un updatable qui contient ce que l'on veut faire
     * @see Updatable
     */
    Updatable getTeamsUpdatable () {
        return new Updatable() {
            @Override
            public void update(Response response) {
                // On initialise les variables des listes
                List<Team> teamsFavorites = new ArrayList<>();

                /*
                  On met à jour les listes avec les données du registre
                  @see AppRegistry
                */
                for (Team team : AppRegistry.teams) {
                    if (favorites.contains(team.getName())) {
                        teamsFavorites.add(team);
                    }
                }

                /*
                  On initialise les adapters pour les recyclerview
                  @see FavoritesAdapter
                */
                adapter_favorites = new FavoritesAdapter(teamsFavorites);

                // On met à jour les adapters pour afficher les résultats
                rv_favorites.setAdapter(adapter_favorites);
            }
        };
    }

}
