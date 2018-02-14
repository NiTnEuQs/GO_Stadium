package com.gostadium.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.gostadium.Activities.ClubDetailsActivity;
import com.gostadium.R;

/**
 * Classe du fragment de géolocalisation (présent dans un onglet de l'écran principal)
 */
public class LocationFragment extends Fragment implements LocationListener {

    // Variable nécessaire pour les permissions
    public final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    // Zoom de base quand on se rend dans l'onglet géolocalisation
    public final static int DEFAULT_ZOOM = 9;

    static MapView mMapView;
    private static GoogleMap googleMap;
    boolean mLocationPermissionGranted;
    Location mLastKnownLocation;
    LatLng mDefaultLocation;
    FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment, container, false);

        initialize(rootView, savedInstanceState);

        return rootView;
    }

    public void initialize(View view, Bundle savedInstanceState) {
        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        // Partie initialisation de la map
        // Construit un FusedLocationProviderClient.
        FragmentActivity fragmentActivity = getActivity();
        assert fragmentActivity != null;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(fragmentActivity);

        try {
            Context context = getContext();
            assert context != null;
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                updateLocationUI();
                getDeviceLocation();
            }
        });
        // Fin partie initialisation de la map
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume(); // needed to get the map to display immediately
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

        // Une fois la permission vérifiée, on actualise l'interface
        updateLocationUI();
    }

    /**
     * Récupère la meilleure localisation et la plus récente de l'appareil, ce qui peut être nulle
     * dans certains cas quand une position n'est pas disponible
     * Les marqueurs sont aussi ajoutés dans cette méthode. Ils permettent d'avoir une vue du
     * stade sur la map.
     * Pour l'instant, les marqueurs sont en durs dans le code, par manque de temps
     * @TODO Généraliser l'initialisation des marqueurs avec les données de la base de données (bdd)
     * @TODO Généraliser l'initialisation des actions des marqueurs avec les données de la bdd
     */
    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                FragmentActivity fragmentActivity = getActivity();
                assert fragmentActivity != null;
                locationResult.addOnCompleteListener(fragmentActivity, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        LatLng me;

                        if (task.isSuccessful()) {
                            // Met la position de la camera sur la map à la position de l'appareil.
                            mLastKnownLocation = (Location) task.getResult();

                            me = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        } else {
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                            me = mDefaultLocation;
                        }

                        /*
                          Partie d'ajout des marqueurs
                          Les marqueurs sont en durs pour le moment, par manque de temps
                        */

                        googleMap.addMarker(new MarkerOptions()
                                .position(me)
                                .title("Moi")
                                .snippet("Ma position actuelle")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(me).zoom(DEFAULT_ZOOM).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        LatLng amiens = new LatLng(47.460431d, -0.530837d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(amiens)
                                .title("Stade de la Licorne")
                                .snippet("Marqueur de la position du stade d'Amiens")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_amiens)));

                        LatLng angers = new LatLng(47.460431d, -0.530837d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(angers)
                                .title("Stade Raymond-Kopa")
                                .snippet("Marqueur de la position du stade d'Angers")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_angers)));

                        LatLng bordeaux = new LatLng(44.897500d, -0.561550d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(bordeaux)
                                .title("Stade Matmut-Atlantique")
                                .snippet("Marqueur de la position du stade de Bordeaux")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_bordeaux)));

                        LatLng caen = new LatLng(49.179487d, -0.396903d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(caen)
                                .title("Stade Michel-d'Ornano")
                                .snippet("Marqueur de la position du stade de Caen")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_caen)));

                        LatLng dijon = new LatLng(47.324382d, 5.068434d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(dijon)
                                .title("Stade Gaston-Gérard")
                                .snippet("Marqueur de la position du stade de Dijon")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_dijon)));

                        LatLng guingamp = new LatLng(48.566086d, -3.164578d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(guingamp)
                                .title("Stade de Roudourou")
                                .snippet("Marqueur de la position du stade de Guingamp")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_guingamp)));

                        LatLng lille = new LatLng(50.611967d, 3.130490d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(lille)
                                .title("Stade Pierre Mauroy")
                                .snippet("Marqueur de la position du stade du LOSC")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_losc_lille)));

                        LatLng lyon = new LatLng(45.765300d, 4.982035d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(lyon)
                                .title("Groupama Stadium")
                                .snippet("Marqueur de la position du stade de Lyon")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_lyon)));

                        LatLng marseille = new LatLng(43.269827d, 5.395993d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(marseille)
                                .title("Orange Vélodrome")
                                .snippet("Marqueur de la position du stade de Marseille")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_marseille)));

                        LatLng metz = new LatLng(49.109778d, 6.159520d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(metz)
                                .title("Stade Saint-Symphorien")
                                .snippet("Marqueur de la position du stade de Metz")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_metz)));

                        LatLng monaco = new LatLng(43.727588d, 7.415502d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(monaco)
                                .title("Stade Louis-II")
                                .snippet("Marqueur de la position du stade de Monaco")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_monaco)));

                        LatLng montpellier = new LatLng(43.622167d, 3.811769d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(montpellier)
                                .title("Stade de la Mosson")
                                .snippet("Marqueur de la position du stade de Montpellier")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_montpellier)));

                        LatLng nantes = new LatLng(47.256023d, -1.524669d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(nantes)
                                .title("Stade de la Beaujoire")
                                .snippet("Marqueur de la position du stade de Nantes")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_nantes)));

                        LatLng nice = new LatLng(43.705059d, 7.192591d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(nice)
                                .title("Allianz Riviera")
                                .snippet("Marqueur de la position du stade de Nice")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_nice)));


                        LatLng paris = new LatLng(48.841436d, 2.253049d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(paris)
                                .title("Parc des Princes")
                                .snippet("Marqueur de la position du stade du PSG")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_paris_saint_germain)));

                        LatLng rennes = new LatLng(48.107503d, -1.712859d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(rennes)
                                .title("Roazhon Park")
                                .snippet("Marqueur de la position du stade de Rennes")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_rennes)));

                        LatLng asse = new LatLng(45.460749d, 4.390044d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(asse)
                                .title("Stade Geoffroy-Guichard")
                                .snippet("Marqueur de la position du stade de Saint-Etienne")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_asse)));

                        LatLng strasbourg = new LatLng(48.560061d, 7.755088d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(strasbourg)
                                .title("Stade de la Meinau")
                                .snippet("Marqueur de la position du stade de Strasbourg")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_strasbourg)));

                        LatLng toulouse = new LatLng(43.583303d, 1.434067d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(toulouse)
                                .title("Stadium de Toulouse")
                                .snippet("Marqueur de la position du stade de Toulouse")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_toulouse)));

                        LatLng troyes = new LatLng(48.560061d, 4.098438d);
                        googleMap.addMarker(new MarkerOptions()
                                .position(troyes)
                                .title("Stade de l'Aube")
                                .snippet("Marqueur de la position du stade de Troyes")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_troyes)));

                        // Fin de partie d'ajout des marqueurs

                        /*
                          Partie de la gestion du clic sur un marqueur.
                          Les actions à l'appui d'un marqueurs sont en durs pour le moment,
                          par manque de temps
                        */

                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Intent intent = new Intent(getContext(), ClubDetailsActivity.class);
                                switch (marker.getTitle()) {
                                    case "Stade de la Licorne":
                                        intent.putExtra("query", "amiens");
                                        startActivity(intent);
                                        break;
                                    case "Stade Raymond-Kopa":
                                        intent.putExtra("query", "angers");
                                        startActivity(intent);
                                        break;
                                    case "Stade Matmut-Atlantique":
                                        intent.putExtra("query", "bordeaux");
                                        startActivity(intent);
                                        break;
                                    case "Stade Michel-d'Ornano":
                                        intent.putExtra("query", "caen");
                                        startActivity(intent);
                                        break;
                                    case "Stade Gaston-Gérard":
                                        intent.putExtra("query", "dijon");
                                        startActivity(intent);
                                        break;
                                    case "Stade de Roudourou":
                                        intent.putExtra("query", "guingamp");
                                        startActivity(intent);
                                        break;
                                    case "Stade Pierre Mauroy":
                                        intent.putExtra("query", "lille");
                                        startActivity(intent);
                                        break;
                                    case "Groupama Stadium":
                                        intent.putExtra("query", "lyon");
                                        startActivity(intent);
                                        break;
                                    case "Orange Vélodrome":
                                        intent.putExtra("query", "marseille");
                                        startActivity(intent);
                                        break;
                                    case "Stade Saint-Symphorien":
                                        intent.putExtra("query", "metz");
                                        startActivity(intent);
                                        break;
                                    case "Stade Louis-II":
                                        intent.putExtra("query", "monaco");
                                        startActivity(intent);
                                        break;
                                    case "Stade de la Mosson":
                                        intent.putExtra("query", "montpellier");
                                        startActivity(intent);
                                        break;
                                    case "Stade de la Beaujoire":
                                        intent.putExtra("query", "nantes");
                                        startActivity(intent);
                                        break;
                                    case "Allianz Riviera":
                                        intent.putExtra("query", "nice");
                                        startActivity(intent);
                                        break;
                                    case "Parc des Princes":
                                        intent.putExtra("query", "paris");
                                        startActivity(intent);
                                        break;
                                    case "Roazhon Park":
                                        intent.putExtra("query", "rennais");
                                        startActivity(intent);
                                        break;
                                    case "Stade Geoffroy-Guichard":
                                        intent.putExtra("query", "saint-étienne");
                                        startActivity(intent);
                                        break;
                                    case "Stade de la Meinau":
                                        intent.putExtra("query", "strasbourg");
                                        startActivity(intent);
                                        break;
                                    case "Stadium de Toulouse":
                                        intent.putExtra("query", "toulouse");
                                        startActivity(intent);
                                        break;
                                    case "Stade de l'Aube":
                                        intent.putExtra("query", "troyes");
                                        startActivity(intent);
                                        break;
                                }

                                return false;
                            }
                        });
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Met à jour l'interface de géolocalisation
     */
    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }

        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Récupère les permissions d'utiliser la géolocalisation
     */
    private void getLocationPermission() {
        /*
         * Excécute une requête de permission de géolocalisation pour que nous puissions récupérer
         * la localisation de l'appareil. Le resultat de la requête est géré par un Callback
         * @see onRequestPermissionsResult
         */
        Context context = getContext();
        assert context != null;
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            FragmentActivity fragmentActivity = getActivity();
            assert fragmentActivity != null;
            ActivityCompat.requestPermissions(fragmentActivity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}
