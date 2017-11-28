package ca.ualberta.cs.routinekeen.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.Markers;
import ca.ualberta.cs.routinekeen.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    LocationManager service;
    public static final int REQUESTION_LOCATION_CODE = 99;
    private FusedLocationProviderClient mFusedLocationClient;
    Button options;
    ArrayList<Markers> toDisplay= new ArrayList<Markers>();
    private Boolean rangeFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        rangeFilter = getIntent().getExtras().getBoolean("radBool");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the MapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        options = (Button) findViewById(R.id.map_options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, MapFilter.class);
                i.putExtra("radBool", rangeFilter);
                startActivity(i);
                finish();
            }
        });

        // If GPS (location) is not enabled, User is sent to the settings to turn it on!
        service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        else {
            getLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // save changes
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTION_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
                }
                return;
        }
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        setUpTestMarkers();
        placeMarkers();

        // Adding a marker in Edmonton and move the Camera
        LatLng edmonton = new LatLng(54.523219, -113.526319);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edmonton, 10));
    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }

    public boolean checkDistance(LatLng latLng, Double range) {
        lastLocation = getLocation();
        float results[] = new float[10];
        Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(),
                latLng.latitude, latLng.longitude, results);
        if (results[0] <= range) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }

    }

    public boolean checkLocationPermission() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESTION_LOCATION_CODE);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESTION_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setUpTestMarkers() {
        storeMarkers("user1", "The King's University", new LatLng(53.525759, -113.416623));
        storeMarkers("user2", "MacEwan University", new LatLng(53.547054, -113.506630));
        storeMarkers("user3", "University of Alberta", new LatLng(53.523219, -113.526319));
    }

    private void storeMarkers(String id, String t, LatLng lL) {
        Markers m = new Markers(id, t, lL);
        toDisplay.add(m);
    }

    private void placeMarkers() {
        for (Markers m : toDisplay) {
            if (rangeFilter){
                if (checkDistance(m.getMarkerLocation(),5000.0)){
                    mMap.addMarker(new MarkerOptions().position(m.getMarkerLocation()).title(m.getMarkerTitle()));
                }
            }
            else{
                mMap.addMarker(new MarkerOptions().position(m.getMarkerLocation()).title(m.getMarkerTitle()));
            }
        }
    }

    /**
     * https://chantisandroid.blogspot.ca/2017/06/get-current-location-example-in-android.html
     * @return
     */
    private Location getLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = service.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                return location;
            }
            else  if (location1 != null) {
                return location1;
            }
            else  if (location2 != null) {
                return location2;

            }
            else{
                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}
