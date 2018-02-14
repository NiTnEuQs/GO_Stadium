package com.gostadium.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gostadium.API.AppRegistry;
import com.gostadium.API.Competition;
import com.gostadium.API.Interfaces.Updatable;
import com.gostadium.Fragments.FavoritesFragment;
import com.gostadium.Fragments.LocationFragment;
import com.gostadium.Fragments.NewsFragment;
import com.gostadium.R;

import retrofit2.Response;

/**
 * La classe de l'activité principale. C'est elle qui contient les onglets généraux (News,
 * Favoris, Géolocalisation)
 */
public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Variables pour le Navigation Drawer
    NavigationView navigationView;
    BottomNavigationView navigation;

    // Variables pour l'authentification
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    //boolean populateDatabase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Initialise l'authentification à Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        /*
          Si l'utilisateur est déjà connecté, on accède à son profil, sinon on le redirige vers
          le formulaire d'authentification
        */
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Initialise la première vue (fragment) sur l'onglet des News
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new NewsFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();

        // Initialise la bar d'action
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialise le Navigation Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Initialise les variables des vues
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Partie importante sur l'authentification avec Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        /*
        if (!populateDatabase) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateDatabase();
                }
            });
            thread.run();
            populateDatabase = true;
        }
        */
    }

    @Override
    protected void onStart() {
        super.onStart();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            /**
             * Une fois que le Navigation Drawer est ouvert, on initialise les variables présentes
             * à l'intérieur avec les informations de l'utilisateur authentifié
             */
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser == null) return;

                TextView name_account = findViewById(R.id.name_account);
                name_account.setText(currentUser.getDisplayName());

                TextView email_account = findViewById(R.id.email_account);
                email_account.setText(currentUser.getEmail());

                //ImageView image_account = findViewById(R.id.image_account);
                // TODO Trouver l'image du compte Google/Facebook et l'affecter à image_account
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // On initialise l'accueil (écran principal) comme selectionnée dans le Navigation Drawer
        navigationView.setCheckedItem(R.id.nav_home);
    }

    /**
     * Dans cette méthode, on gère le fait que l'utilisateur clic sur l'action de revenir en arrière
     * Si l'utilisateur est dans le Navigation Drawer, cela le ferme simplement
     * Si l'utilisateur est dans un autre onglet que celui des News, on le redirige vers les News
     * Si l'utilisateur est dans l'onglet des News, on ferme l'application
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (navigation.getSelectedItemId() != R.id.navigation_news) {
                navigation.setSelectedItemId(R.id.navigation_news);
            } else {
                finish();
            }
        }
    }

    /**
     * Gère les actions possibles dans le menu de la barre. Ici on ne gère que la barre de recherche
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Gère les requêtes faites par l'utilisateur dans la barre de recherche
             * @param query Chaîne de caractère de la recherche
             * @return Vrai si la requête est bonne, faux sinon
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                boolean isAGoodQuery = (query != null && !query.equals(""));

                if (isAGoodQuery) {
                    Intent intent = new Intent(getApplicationContext(), ClubDetailsActivity.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }

                return isAGoodQuery;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    /**
     * Gère le clic sur un des menus du Navigation Drawer
     * @param item Menu selectionné
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id != R.id.nav_home) {
            selectItem(id);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Gère l'action à faire quand l'utilisateur clic sur un menu du Navigation Drawer
     * @param position L'index de la position du menu dans la liste
     */
    private void selectItem(int position) {
        Intent intent;

        switch (position) {
            /*
            case R.id.nav_favorites:
                intent = new Intent(this, FavoritesActivity.class);
                break;
            case R.id.nav_manage:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.nav_help:
                intent = new Intent(this, HelpActivity.class);
                break;
            */
            case R.id.nav_logout:
                signOut();
                intent = new Intent(this, LoginActivity.class);
                break;
            default:
                intent = new Intent(this, MainMenuActivity.class);
                break;
        }

        startActivity(intent);
    }

    /**
     * Gère les clics sur les onglets de l'écran principal.
     * On ne change juste qu'un Fragment présent en dessous de la barre des onglets
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_news:
                    fragment = new NewsFragment();
                    break;
                case R.id.navigation_favorites:
                    fragment = new FavoritesFragment();
                    break;
                case R.id.navigation_location:
                    fragment = new LocationFragment();
                    break;
                /*
                case R.id.navigation_share:
                    fragment = new ShareFragment();
                    break;
                */
            }

            assert fragment != null;

            transaction.replace(R.id.content_fragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
    };

    /**
     * Gère la déconnexion de tous les services
     */
    private void signOut() {
        // Firebase sign out
        firebaseAuth.signOut();

        // Google sign out
        googleSignInClient.signOut();

        //Facebook sign out
        LoginManager.getInstance().logOut();
    }

    /**
     * Gère le clic sur le bouton des favoris
     */
    public void onClickFavorite(View view) {
        TextView tv_club = view.findViewById(R.id.tv_favorite);
        Intent intent = new Intent(getApplicationContext(), ClubDetailsActivity.class);
        intent.putExtra("query", tv_club.getText());
        startActivity(intent);
    }

    /**
     * Gère le clic sur l'équipe à domicile
     */
    public void onClickHomeTeam(View view) {
        TextView tv_club = view.findViewById(R.id.tv_result_home_team);
        Intent intent = new Intent(getApplicationContext(), ClubDetailsActivity.class);
        intent.putExtra("query", tv_club.getText());
        startActivity(intent);
    }

    /**
     * Gère le clic sur l'équipe à l'extérieur
     */
    public void onClickAwayTeam(View view) {
        TextView tv_club = view.findViewById(R.id.tv_result_away_team);
        Intent intent = new Intent(getApplicationContext(), ClubDetailsActivity.class);
        intent.putExtra("query", tv_club.getText());
        startActivity(intent);
    }

    /**
     * Gère l'initialisation de la base de données Firebase.
     * On ne l'utilise pas ici, on utilise plutôt un registre qui nous permet de stocker les
     * informations de l'API
     * @see AppRegistry
     */
    void updateDatabase() {
        AppRegistry.updateCompetitions(new Updatable() {
            @Override
            public void update(Response response) {
                for (Competition competition : AppRegistry.competitions) {
                    AppRegistry.updateTeams(null, competition.get_links().getTeams().getHref());
                    AppRegistry.updateFixtures(null, competition.get_links().getFixtures().getHref());
                    AppRegistry.updateLeagueTable(null, competition.get_links().getLeagueTable().getHref());
                }
            }
        });

        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference rootRef = database.getReference();

        AppRegistry.updateCompetitions(new Updatable() {
            @Override
            public void update(Response response) {
                final DatabaseReference competitionsRef = rootRef.child("competitions");
                competitionsRef.setValue(AppRegistry.competitions);

                competitionsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            final int finalI = i;

                            Competition competition = data.getValue(Competition.class);
                            assert competition != null;

                            AppRegistry.updateCompetitionTeams(new Updatable() {
                                @Override
                                public void update(Response response) {
                                    Teams teams = (Teams) response.body();
                                    List<Team> res = null;

                                    if (teams != null) {
                                        res = teams.getTeams();
                                    } else {
                                        Log.e("Invalid", "" + finalI);
                                    }

                                    if (res != null) competitionsRef.child(String.valueOf(finalI)).child("teams").setValue(res);
                                    //competitionsRef.child(String.valueOf(finalI)).child("teams").setValue(AppRegistry.competition_teams.get(finalI));
                                }
                            }, competition.getId());

                            AppRegistry.updateCompetitionFixtures(new Updatable() {
                                @Override
                                public void update(Response response) {
                                    Fixtures fixtures = (Fixtures) response.body();
                                    List<Fixture> res = null;

                                    if (fixtures != null) {
                                        res = fixtures.getFixtures();
                                    } else {
                                        Log.e("Invalid", "" + finalI);
                                    }

                                    if (res != null) competitionsRef.child(String.valueOf(finalI)).child("fixtures").setValue(res);
                                    //competitionsRef.child(String.valueOf(finalI)).child("fixtures").setValue(AppRegistry.competition_fixtures.get(finalI));
                                }
                            }, competition.getId());

                            i++;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        */
    }

}
