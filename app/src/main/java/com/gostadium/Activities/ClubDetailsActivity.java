package com.gostadium.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.gostadium.R;

public class ClubDetailsActivity extends AppCompatActivity {

    public static boolean loscIsFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        setupActionBar();

        String query = getIntent().getStringExtra("query");
        query = "LOSC Lille";

        setTitle(query);

        final FloatingActionButton fab = findViewById(R.id.fab_favorite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loscIsFavorite) {
                    fab.setImageResource(R.drawable.ic_menu_favorites_white);
                    loscIsFavorite = false;
                    Snackbar.make(view, "LOSC Lille a été enlevé de vos favoris", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    fab.setImageResource(R.drawable.ic_menu_star_white);
                    loscIsFavorite = true;
                    Snackbar.make(view, "LOSC Lille a été ajouté à vos favoris", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });

        TextView tv_route = findViewById(R.id.tv_route);
        tv_route.setText("Itinéraire vers " + query);

        TextView tv_description = findViewById(R.id.tv_description);
        tv_description.setText("Le LOSC Lille, couramment abrégé en LOSC\nou Lille OSC, est un club de football français. Fondé à Lille en septembre 1944, il est issu de la fusion de deux clubs basés dans différents quartiers de la ville, l'Olympique lillois et le Sporting Club fivois respectivement créés en 1902 et 1901.");
    }

    public void onClickRoute(View view) {
        LatLng losc = new LatLng(50.611967d, 3.130490d);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + losc.latitude + "," + losc.longitude));
        startActivity(browserIntent);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
