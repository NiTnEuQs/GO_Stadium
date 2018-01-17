package com.gostadium;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gostadium.Fragments.LocationFragment;
import com.gostadium.Fragments.NewsFragment;
import com.gostadium.Fragments.SearchFragment;
import com.gostadium.Fragments.ShareFragment;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    BottomNavigationView navigation;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        /* Si l'utilisateur est déjà connecté, on accède à son profil, sinon on le redirige vers
         * le formulaire d'authentification
         */

        firebaseAuth = FirebaseAuth.getInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new NewsFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser == null) return;

                TextView name_account = findViewById(R.id.name_account);
                name_account.setText(currentUser.getDisplayName());

                TextView email_account = findViewById(R.id.email_account);
                email_account.setText(currentUser.getEmail());

                ImageView image_account = findViewById(R.id.image_account);
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

        navigationView.setCheckedItem(R.id.nav_home);
    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id != R.id.nav_home) {
            selectItem(id);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectItem(int position) {
        Intent intent;

        switch (position) {
            case R.id.nav_favorites:
                intent = new Intent(this, FavoritesActivity.class);
                break;
            case R.id.nav_manage:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.nav_help:
                intent = new Intent(this, HelpActivity.class);
                break;
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
                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    break;
                case R.id.navigation_location:
                    fragment = new LocationFragment();
                    break;
                case R.id.navigation_share:
                    fragment = new ShareFragment();
                    break;
            }

            assert fragment != null;

            transaction.replace(R.id.content_fragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
    };

    private void signOut() {
        // Firebase sign out
        firebaseAuth.signOut();

        // Google sign out
        googleSignInClient.signOut();

        //Facebook sign out
        LoginManager.getInstance().logOut();
    }

}
