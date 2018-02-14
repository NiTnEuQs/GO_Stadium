package com.gostadium.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.gostadium.API.AppRegistry;
import com.gostadium.API.Fixture;
import com.gostadium.API.Interfaces.Updatable;
import com.gostadium.API.Team;
import com.gostadium.Adapters.MatchAdapter;
import com.gostadium.Fragments.FavoritesFragment;
import com.gostadium.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * La classe qui gère la partie des informations du club
 */
public class ClubDetailsActivity extends AppCompatActivity {

    // Code de la compétition (Ligue 1). Temporaire.
    final static int leagueId = 450;

    // Barre d'action de l'écran
    Toolbar toolbar;

    /**
     * Variables des recyclerview présents sur l'écran
     */
    RecyclerView rv_last_result;
    RecyclerView rv_pendingMatch;
    RecyclerView rv_calendar;

    /**
     * Variables des adapters pour les recyclerview
     */
    MatchAdapter adapter_last_result;
    MatchAdapter adapter_pendingMatch;
    MatchAdapter adapter_calendar;

    /**
     * Variables des listes utilisées par les adapters
     */
    public static List<Fixture> last_result = new ArrayList<>();
    public static List<Fixture> pendingMatch = new ArrayList<>();
    public static List<Fixture> calendar = new ArrayList<>();

    // Requête de l'équipe à afficher
    Team finalTeamQuery = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        initialize();
    }

    /**
     * Initialise la vue et met à jour le registre avec les données de l'API
     */
    public void initialize() {
        // Met en place la barre d'action
        setupActionBar();

        // Initialise les variables du layout
        rv_last_result = findViewById(R.id.rv_last_result);
        rv_last_result.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rv_calendar = findViewById(R.id.rv_calendar);
        rv_calendar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rv_pendingMatch = findViewById(R.id.rv_pendingMatchs);
        rv_pendingMatch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        /*
          Met à jour les équipe de la compétition avec les données de l'API grâce au code de la
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
                String query = getIntent().getStringExtra("query");
                Team teamQuery = null;

                int i = 0;
                while (teamQuery == null && i < AppRegistry.teams.size()) {
                    Team team = AppRegistry.teams.get(i);
                    if (team.getName().toLowerCase().contains(query.toLowerCase())) {
                        teamQuery = team;
                    }
                    i++;
                }

                if (teamQuery == null) return;

                String[] str = teamQuery.get_links().getSelf().getHref().split("/");
                int teamId = Integer.parseInt(str[str.length - 1]);

                AppRegistry.updateTeamFixtures(getTeamFixturesUpdatable(), teamId);

                final FloatingActionButton fab = findViewById(R.id.fab_favorite);
                finalTeamQuery = teamQuery;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (FavoritesFragment.favorites.contains(finalTeamQuery.getName())) {
                            fab.setImageResource(R.drawable.ic_menu_favorites_white);
                            FavoritesFragment.favorites.remove(finalTeamQuery.getName());
                            Snackbar.make(view, finalTeamQuery.getName() + " a été enlevé de vos favoris", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        } else {
                            fab.setImageResource(R.drawable.ic_menu_star_white);
                            FavoritesFragment.favorites.add(finalTeamQuery.getName());
                            Snackbar.make(view, finalTeamQuery.getName() + " a été ajouté à vos favoris", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }
                });

                if (FavoritesFragment.favorites.contains(finalTeamQuery.getName())) {
                    fab.setImageResource(R.drawable.ic_menu_star_white);
                } else {
                    fab.setImageResource(R.drawable.ic_menu_favorites_white);
                }

                CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
                toolbarLayout.setTitle(finalTeamQuery.getName());

                TextView tv_route = findViewById(R.id.tv_route);
                tv_route.setText(String.format("Itinéraire vers %s", finalTeamQuery.getName()));

                TextView tv_description = findViewById(R.id.tv_description);
                tv_description.setText("Le LOSC Lille, couramment abrégé en LOSC\nou Lille OSC, est un club de football français. Fondé à Lille en septembre 1944, il est issu de la fusion de deux clubs basés dans différents quartiers de la ville, l'Olympique lillois et le Sporting Club fivois respectivement créés en 1902 et 1901.");
            }
        };
    }

    /**
     * Une fois la requête terminée, on est redirigés vers cette méthode et avons accès
     * à tous les matchs de l'équipe, ce qui permet de mettre à jour les informations à l'écran
     * @return Un updatable qui contient ce que l'on veut faire
     * @see Updatable
     */
    Updatable getTeamFixturesUpdatable () {
        return new Updatable() {
            @Override
            public void update(Response response) {
                /*
                  Met à jour la compétition avec les données de l'API grâce au code de la
                  compétition leagueId.
                  Une fois la requête terminée, on est redirigés vers la méthode getTeamsUpdatable
                */
                AppRegistry.updateCompetition(getCompetitionUpdatable(), leagueId);
            }
        };
    }

    /**
     * Une fois la requête terminée, on est redirigés vers cette méthode et avons accès
     * à toula competition, ce qui permet de mettre à jour les informations à l'écran
     * @return Un updatable qui contient ce que l'on veut faire
     * @see Updatable
     */
    Updatable getCompetitionUpdatable() {
        return new Updatable() {
            @Override
            public void update(Response response) {
                // On initialise les variables des listes
                last_result = new ArrayList<>();
                pendingMatch = new ArrayList<>();
                calendar = new ArrayList<>();

                /*
                  On met à jour les listes avec les données du registre
                  @see AppRegistry
                */
                for (int i = AppRegistry.team_fixtures.size() - 1; i >= 0; i--) {
                    Fixture fixture = AppRegistry.team_fixtures.get(i);
                    if (fixture.getStatus().equals("FINISHED")) {
                        last_result.add(fixture);
                        break;
                    }
                }

                Fixture nextMatch = null;
                for (Fixture fixture : AppRegistry.team_fixtures) {
                    if (nextMatch == null && fixture.getStatus().equals("SCHEDULED")) {
                        nextMatch = fixture;
                    }
                    if (fixture.getStatus().equals("TIMED")) {
                        pendingMatch.add(fixture);
                        break;
                    }
                }

                if (pendingMatch.size() <= 0) {
                    pendingMatch.add(nextMatch);
                }

                calendar.addAll(AppRegistry.team_fixtures);

                /*
                  On initialise les adapters pour les recyclerview
                  @see MatchAdapter
                */
                adapter_last_result = new MatchAdapter(last_result);
                adapter_calendar = new MatchAdapter(calendar);
                adapter_pendingMatch = new MatchAdapter(pendingMatch);

                // On met à jour les adapters pour afficher les résultats
                rv_last_result.setAdapter(adapter_last_result);
                rv_calendar.setAdapter(adapter_calendar);
                rv_pendingMatch.setAdapter(adapter_pendingMatch);
            }
        };
    }

    /**
     * Gère le clic sur le bouton de l'itinéraire en fonction du nom court de l'équipe
     * et lance l'application google maps au bon endroit
     */
    public void onClickRoute(View view) {
        LatLng position = null;

        switch (finalTeamQuery.getShortName()) {
            case "Amiens":
                position = new LatLng(47.460431d, -0.530837d);
                break;
            case "Angers":
                position = new LatLng(47.460431d, -0.530837d);
                break;
            case "Bordeaux":
                position = new LatLng(44.897500d, -0.561550d);
                break;
            case "Caen":
                position = new LatLng(49.179487d, -0.396903d);
                break;
            case "Dijon":
                position = new LatLng(47.324382d, 5.068434d);
                break;
            case "Guingamp":
                position = new LatLng(48.566086d, -3.164578d);
                break;
            case "Lille":
                position = new LatLng(50.611967d, 3.130490d);
                break;
            case "Lyon":
                position = new LatLng(45.765300d, 4.982035d);
                break;
            case "Marseille":
                position = new LatLng(43.269827d, 5.395993d);
                break;
            case "Metz":
                position = new LatLng(49.109778d, 6.159520d);
                break;
            case "Monaco":
                position = new LatLng(43.727588d, 7.415502d);
                break;
            case "Montpellier":
                position = new LatLng(43.622167d, 3.811769d);
                break;
            case "Nantes":
                position = new LatLng(47.256023d, -1.524669d);
                break;
            case "Nice":
                position = new LatLng(43.705059d, 7.192591d);
                break;
            case "PSG":
                position = new LatLng(48.841436d, 2.253049d);
                break;
            case "Rennes":
                position = new LatLng(48.107503d, -1.712859d);
                break;
            case "St. Etienne":
                position = new LatLng(45.460749d, 4.390044d);
                break;
            case "Strasbourg":
                position = new LatLng(48.560061d, 7.755088d);
                break;
            case "Toulouse":
                position = new LatLng(43.583303d, 1.434067d);
                break;
            case "Troyes":
                position = new LatLng(48.560061d, 4.098438d);
                break;
        }

        if (position == null) return;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + position.latitude + "," + position.longitude));
        startActivity(browserIntent);
    }

    /**
     * Initialise l'{@link android.app.ActionBar}, si l'API est disponible.
     */
    private void setupActionBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Gère l'action sur le bouton de retour
     * @param item Item de la flèche de retour
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
