package com.example.android.sfcrimeheatmap.activities.heatmap;

import android.os.Bundle;

import com.example.android.sfcrimeheatmap.R;
import com.example.android.sfcrimeheatmap.activities.BaseActivity;
import com.example.android.sfcrimeheatmap.activities.BaseActivityModule;
import com.example.android.sfcrimeheatmap.application.CMAppComponent;
import com.example.android.sfcrimeheatmap.rest.models.CrimeIncident;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import javax.inject.Inject;

public class CrimeMapActivity extends BaseActivity implements OnMapReadyCallback, CrimeMapView {

    private GoogleMap mMap;

    @Inject
    CrimeMapActivityPresenter presenter;

    @Override
    protected void setupDataInjection(CMAppComponent appComponent) {
        DaggerCrimeMapActivityComponent
                .builder()
                .cMAppComponent(appComponent)
                .crimeMapActivityModule(new CrimeMapActivityModule(this))
                .baseActivityModule(new BaseActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        presenter.loadMapMarkers();
    }

    @Override
    public void showMarkers(ArrayList<CrimeIncident> body) {

    }
}