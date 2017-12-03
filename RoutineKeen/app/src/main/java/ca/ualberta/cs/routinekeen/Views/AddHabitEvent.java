package ca.ualberta.cs.routinekeen.Views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Helpers.PhotoHelpers;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class AddHabitEvent extends AppCompatActivity {
    private Spinner spinner;
    private EditText eventTitle;
    private EditText eventComment;
    private ImageButton photoImageButton;

    private LocationManager locationManager;
    private LocationManager service;
    private Location location;

    private HabitEvent toAddEvent;
    private String eventType;
    private byte[] photoByteArray;

    private static final int REQUEST_LOCATION = 1;
    protected static final int REQUEST_SELECT_IMAGE = 3;
    protected static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2;
    protected static final int IMAGE_MAX_BYTES = 65536;
    protected static final int LENGTH = (int) Math.floor(Math.sqrt(IMAGE_MAX_BYTES));

    private ArrayList<String> typeList;
    private ArrayAdapter<String> typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        IOManager.initManager(this.getApplicationContext());
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
        service = (LocationManager) getSystemService(LOCATION_SERVICE);

        spinner = (Spinner) findViewById(R.id.habitTypeSpinner);
        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventComment = (EditText) findViewById(R.id.eventComment);
        photoImageButton = (ImageButton) findViewById(R.id.imageButtonPhoto);
        initListeners();
    }

    @Override
    public void onStart(){
        super.onStart();
        typeList = new ArrayList<String>(HabitListController.getTypeList());
        typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(typeAdapter);
    }

    public void addEvent(View view) {
        if(validationSuccess()) {
            try {
                LatLng newEventLocation = new LatLng(location.getLatitude(), location.getLongitude());
                toAddEvent = new HabitEvent(eventTitle.getText().toString(), eventType,
                        eventComment.getText().toString(), newEventLocation, photoByteArray);
            } catch (Exception e) {
                // no location attached OR location error
                toAddEvent = new HabitEvent(eventTitle.getText().toString(), eventType,
                        eventComment.getText().toString());
                toAddEvent.setPhoto(photoByteArray);
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
//        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        if (!enabled) {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        } else {
//            getDeviceLoc();
//        }

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

        } else {
            Location location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = service.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);
            if (location != null) {
                return location;
            } else  if (location1 != null) {
                return location1;
            } else  if (location2 != null) {
                return location2;
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
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void selectImage() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_IMAGE);
            } else {
                // permission denied, don't open gallery
                Log.d("AHELog", "permission denied");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                InputStream image_stream = getContentResolver().openInputStream(imageUri);
                Bitmap thumbImage = PhotoHelpers.convertImageStreamToBitMap(image_stream, LENGTH, LENGTH);
                image_stream = getContentResolver().openInputStream(imageUri);
                photoImageButton.setImageBitmap(thumbImage); // reset the stream
                photoByteArray = PhotoHelpers.convertImageStreamToByteArray(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("AHELog", "FNF except");
            } catch (Exception e) {
                Log.d("AHELog", "Unknown except");
                Log.e("AHELog", "unknown", e);
            }
        }
    }

    private void initListeners(){
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

        photoImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
}
