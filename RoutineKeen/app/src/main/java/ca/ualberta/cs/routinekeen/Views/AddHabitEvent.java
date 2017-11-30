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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class AddHabitEvent extends AppCompatActivity {


    Location location;
    HabitEvent toAddEvent;
    LocationManager service;
    private Spinner spinner;
    private String eventType;
    private EditText eventTitle;
    private EditText eventComment;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        IOManager.initManager(this.getApplicationContext());
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        service = (LocationManager) getSystemService(LOCATION_SERVICE);

        spinner = (Spinner) findViewById(R.id.habitTypeSpinner);
        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventComment = (EditText) findViewById(R.id.eventComment);
        HabitListController.getHabitList();
        ArrayList<String> typeList = new ArrayList<String>(HabitListController.getTypeList());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                typeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                eventType =  (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void addEvent(View view) {
        if(validationSuccess()) {
            try {
                LatLng newEventLocation = new LatLng(location.getLatitude(), location.getLongitude());
                toAddEvent = new HabitEvent(eventTitle.getText().toString(), eventType,
                        eventComment.getText().toString(), newEventLocation);
            } catch (Exception e) {
                // no location attached OR location error
                toAddEvent = new HabitEvent(eventTitle.getText().toString(), eventType,
                        eventComment.getText().toString());
            }
            HabitHistoryController.addHabitEvent(toAddEvent);
            finish();
        }
    }

    private boolean validationSuccess() {
        if (eventTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a title for event.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (eventComment.getText().toString().length() > 20) {
            Toast.makeText(this, "Habit event comment much be less than 20 characters.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void attachLocation(View view) {
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {
            getDeviceLoc();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                location = getDeviceLoc();
                Toast.makeText(this,"Location Attached",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this,"Unable to trace your location",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * ref: https://chantisandroid.blogspot.ca/2017/06/get-current-location-example-in-android.html
     * @return current location
     */
    private Location getDeviceLoc() {
        if (ActivityCompat.checkSelfPermission(AddHabitEvent.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (AddHabitEvent.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddHabitEvent.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        }
      else {
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
        }
        return null;
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
