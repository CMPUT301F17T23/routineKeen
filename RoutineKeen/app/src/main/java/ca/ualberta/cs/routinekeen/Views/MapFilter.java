package ca.ualberta.cs.routinekeen.Views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.NetworkDataManager;
import ca.ualberta.cs.routinekeen.Controllers.UserListController;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.Markers;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;

/**
 * User has the option of filtering the markers he/she would like to see on the map.
 * Created by tiakindele on 2017-11-24.
 */

public class MapFilter extends AppCompatActivity{
    Boolean radiusBool = false;     // true if the user only wants nearby events shown
    Boolean personalBool = false;
    Boolean followingBool = false;
    Boolean recentBool = false;
    LocationManager service;
    CheckBox pCheckbox;
    CheckBox nCheckbox;
    Collection<HabitEvent> events;
    private GoogleApiClient client;
    ArrayList<Markers> toDisplay = new ArrayList<Markers>();
    Collection<HabitEvent> eventsToDisplay = new ArrayList<HabitEvent>();
    private Location lastLocation;                          // Last known location of device
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private int isCheckedFlag = 0;
    int isFiltered = 0;
    int markerCount = 0;
    private static final int REQUEST_LOCATION = 1;
    public static final int REQUESTION_LOCATION_CODE = 99;
    private FusedLocationProviderClient mFusedLocationClient;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<HabitEvent> tempArray = new ArrayList<>();
//    private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
//    private ArrayList<ArrayList<HabitEvent>> allEvents = new ArrayList<>();

    /**
     * On create, initialize page.
     * Also, check if checkboxes are supposed to be checked or not.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_filter);
        resetMarkers();
        eventsToDisplay.clear();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
        service = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Turn on personal setting my default
        pCheckbox = (CheckBox)findViewById(R.id.personalCheckBox);
        pCheckbox.setChecked(true);
        personalBool = true;
        isCheckedFlag += 1;
    }

    /**
     * Apply filter to the map view.
     * Send the user's choice to only see nearby events to MapsActivity.class through putExtra
     * @param view
     */
    public void applyFilter(View view) throws NetworkUnavailableException {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            if (isCheckedFlag > 0) {
                eventsToDisplay.clear();
                countMarkers();
                if (markerCount > 0) {
                    Intent i = new Intent(MapFilter.this, MapsActivity.class);
                    if (toDisplay.size() != 0) {
                        resetMarkers();
                    }
                    setUpMarkers();
                    i.putExtra("toDisplay", toDisplay);
                    if (isFiltered > 0) {
                        Toast.makeText(this, "Filter Applied!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Showing Events", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(i);
                } else {
                    Toast.makeText(this, "No habit event(s) to display", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select: Personal and/or Following", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpMarkers() throws NetworkUnavailableException {
        Boolean eventsError = Boolean.FALSE;
        try {
            for (HabitEvent e : eventsToDisplay) {
                //pass
            }
        } catch (Exception e) {
            eventsError = Boolean.TRUE;
            Log.d("tag1", "e not okay");
        }
        if (!eventsError) {
            for (HabitEvent e : eventsToDisplay) {
                if (e.getLocation() != null) {
                    if (radiusBool) {
                        if (checkDistance(e.getLocation(), 5000.0)) {
                            String id = e.getAssociatedUserID();
                            String locName = e.getTitle();
                            LatLng location = e.getLocation();
                            storeMarkers(id, locName, location);
                        }
                    } else {
                        String id = e.getAssociatedUserID();
                        String locName = e.getTitle();
                        LatLng location = e.getLocation();
                        storeMarkers(id, locName, location);
                    }
                }

            }
        }
    }

    private void countMarkers() throws NetworkUnavailableException {
        Boolean eventsError = Boolean.FALSE;



        if (personalBool) {
            getUserEvents();
        }
        if (followingBool) {
            getFollowingEvents();
        }

        try {
            for (HabitEvent e : eventsToDisplay) {
                //pass
            }
        } catch (Exception e) {
            eventsError = Boolean.TRUE;
            Log.d("tag1", "e not okay");
        }
        if (!eventsError) {
            for (HabitEvent e : eventsToDisplay) {
                if (e.getLocation() != null) {
//                    String id = e.getAssociatedUserID();
//                    String locName = e.getTitle();
//                    LatLng location = e.getLocation();
//                    storeMarkers(id, locName, location);
                    markerCount += 1;
                }
            }
        }
    }

    /**
     * Check distance between current location and this locations
     * @param latLng location compared to current location
     * @param range distance to filter events by
     * @return true if within range; false otherwise
     */
    public boolean checkDistance(LatLng latLng, Double range) {
        lastLocation = getDeviceLoc();
        float results[] = new float[10];
        Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(),
                latLng.latitude, latLng.longitude, results);
        if (results[0] <= range) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Store markers to array of markers to be called when they are ready to be placed on the map.
     * @param id User ID
     * @param t Habit Event Title
     * @param lL Location of habit event in LatLng form
     */
    private void storeMarkers(String id, String t, LatLng lL) {
        Markers m = new Markers(id, t, lL);
        toDisplay.add(m);
    }

    private void resetMarkers() {
        toDisplay.clear();
    }

    private void getUserEvents() {
        events = HabitHistoryController.getHabitHistory().getEvents();
        for (HabitEvent elem : events) {
            eventsToDisplay.add(elem);
        }
    }

    /**
     * When a checkbox is clicked on the map filter page, handle it here
     * @param view
     */
    public void onFilterCheckboxClicked(View view) {

        // True if the box has been checked
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.personalCheckBox:
                if (checked) {
                    // include personal events
                    isCheckedFlag += 1;
                    personalBool = true;
                }
                else {
                    // do not include personal events
                    isCheckedFlag -= 1;
                    personalBool = false;
                }
                break;
            case R.id.followingCheckBox:
                if (checked) {
                    // include following events
                    isCheckedFlag += 1;
                    followingBool = true;
                }
                else {
                    //do not include following events
                    isCheckedFlag -= 1;
                    followingBool = false;
                }
                break;
            case R.id.recentCheckBox:
                if (checked) {
                    // include only recent events
                    isFiltered += 1;
                    recentBool = true;
                }
                else {
                    //include all events
                    isFiltered -= 1;
                    recentBool = false;
                }
                break;
            case R.id.radiusCheckBox:
                if (checked) { // include only recent radius
                    isFiltered += 1;
                    radiusBool = true;
                }
                else{
                    isFiltered -= 1;
                    radiusBool = false;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // save changes
    }

    /**
     * Get the current location of the device
     * ref: https://chantisandroid.blogspot.ca/2017/06/get-current-location-example-in-android.html
     * @return current location
     */
    private Location getDeviceLoc() {
        if (ActivityCompat.checkSelfPermission(MapFilter.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MapFilter.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapFilter.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = service.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                return location;
            } else if (location1 != null) {
                return location1;
            } else if (location2 != null) {
                return location2;
            } else {
                Toast.makeText(this, "Unable to trace your location", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    protected void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enable location")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        // Turn on personal setting my default
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Check if application has granted GPS location permission
     * @return true if above line is true; false otherwise.
     */
    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESTION_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESTION_LOCATION_CODE);
            }
            return false;
        } else
            return true;
    }

    private void getFollowingEvents() throws NetworkUnavailableException {
        users.clear();
        users.addAll(UserListController.getUserList().getUsers());

        for (User s : users) {
            try {
                String username = s.getUsername();
//                Log.d("tag1", "username: "+username);
                username = "Hugh";
                IOManager.getManager().getUser(username);
                tempArray = (ArrayList<HabitEvent>) NetworkDataManager.GetUserHabitEvents(IOManager
                        .getManager().getUser(username).getUserID()).getEvents();
                for (HabitEvent he : tempArray) {
                    eventsToDisplay.add(he);
                }
            } catch (Exception e) {
                Log.d("tag1", "Could not complete for User: "+s);
            }

        }
//        Log.d("tag1", "string"+String.valueOf(eventsToDisplay));
    }
}
