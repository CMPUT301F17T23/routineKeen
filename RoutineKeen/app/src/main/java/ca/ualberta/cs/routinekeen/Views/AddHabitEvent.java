package ca.ualberta.cs.routinekeen.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class AddHabitEvent extends AppCompatActivity {

    protected static final int REQUEST_SELECT_IMAGE = 1;
    protected static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2;

    protected static final int IMAGE_MAX_BYTES = 65536;
    protected static final int LENGTH = (int) Math.floor(Math.sqrt(IMAGE_MAX_BYTES));
    private ImageView eventImageView;
    private Button selectImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        IOManager.initManager(this.getApplicationContext());

        eventImageView = (ImageView) findViewById(R.id.addEventImageView);
        selectImageButton = (Button)findViewById(R.id.buttonSelectImage);

        selectImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public void addEvent(View view)
    {
        //Edit texts, there will be more
        EditText eventTitle = (EditText) findViewById(R.id.eventTitle);
        EditText eventComment = (EditText) findViewById(R.id.eventComment);

        assert eventTitle != null;
        String newEventTitle = eventTitle.getText().toString();
        assert eventComment != null;
        String newEventComment = eventComment.getText().toString();

        if(newEventTitle.isEmpty() || newEventTitle.equals("\n"))
        {
            newEventTitle = "No Title";
        }
        if(newEventComment.isEmpty() || newEventComment.equals("\n"))
        {
            newEventComment = "No Comments";
        }
        HabitEvent toAddEvent = new HabitEvent(newEventTitle,newEventComment);
        HabitHistoryController.addHabitEvent(toAddEvent);
        finish();
    }

    public void selectImage() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);

        //Intent intent = new Intent(Intent.ACTION_PICK,
        //        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(intent, REQUEST_SELECT_IMAGE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_SELECT_IMAGE);
                } else {
                    // permission denied, don't open gallery
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();

                    try {
                        // get selected image in bitmap form
                        InputStream image_stream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(image_stream);

                        // convert selected image to thumbnail smaller than IMAGE_MAX_BYTES
                        Bitmap thumbImage = ThumbnailUtils.extractThumbnail(selectedImage,
                                LENGTH, LENGTH);
                        // Taken from: http://www.rogerethomas.com/blog/generating-square-cropped-thumbnails-in-android-java
                        // on Nov 25, 2017

                        // display new image in imageview
                        eventImageView.setImageBitmap(thumbImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.d("AHELog", "FNF except");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("AHELog", "IO except");
                    } catch (Exception e) {
                        Log.d("AHELog", "Unknown except");
                        Log.e("AHELog", "unknown", e);
                    }
                }
        }
    }
}
