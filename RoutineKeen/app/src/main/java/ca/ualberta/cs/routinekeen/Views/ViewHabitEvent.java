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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class ViewHabitEvent extends AppCompatActivity {
    private String eventType;
    private Integer index;
    private EditText eventTitle;
    private EditText eventComment;

    Location location;
    LocationManager service;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);
        IOManager.initManager(getApplicationContext());
        //Grab data from previous activity
        Intent intent = getIntent();
        index = (Integer) intent.getSerializableExtra("View Event");
        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        HabitListController.getHabitList();

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        service = (LocationManager) getSystemService(LOCATION_SERVICE);

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
        //Show respective values in view
        TextView newEventTitle = (TextView) findViewById(R.id.eventTitle);
        newEventTitle.setText(HabitHistoryController.getHabitEvent(index).getTitle());

        TextView newEventComment = (TextView) findViewById(R.id.eventComment);
        newEventComment.setText(HabitHistoryController.getHabitEvent(index).getComment());
    }

    public void saveEvent(View view)
    {
        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventComment = (EditText) findViewById(R.id.eventComment);
        if(validationSuccess()) {

            HabitHistoryController.getHabitHistory().getHabitEvent(index).setTitle(eventTitle.getText().toString());
            HabitHistoryController.saveHabitHistory();
            HabitHistoryController.getHabitHistory().getHabitEvent(index).setComment(eventComment.getText().toString());
            HabitHistoryController.saveHabitHistory();
            HabitHistoryController.getHabitHistory().getHabitEvent(index).setEventHabitType(eventType);

            try {
                LatLng newEventLocation = new LatLng(location.getLatitude(), location.getLongitude());
                HabitHistoryController.getHabitHistory().getHabitEvent(index).setLocation(newEventLocation);
                Log.d("tag1", "location success!"+String.valueOf(newEventLocation));
            } catch (Exception e) {
                // no new location attached OR location error
                // do nothing
            }
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

    public void delEvent(View view)
    {
        HabitEvent delEvent = HabitHistoryController.getHabitEvent(index);
        HabitHistoryController.removeHabitEvent(delEvent);
        finish();
    }

    public void attachUpdatedLocation(View view) {
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
                Toast.makeText(this,"Location Updated",Toast.LENGTH_SHORT).show();
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
        if (ActivityCompat.checkSelfPermission(ViewHabitEvent.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (ViewHabitEvent.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ViewHabitEvent.this,
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
