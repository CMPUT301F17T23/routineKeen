package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    protected static final int IMAGE_MAX_BYTES = 65536;
    protected static final int LENGTH = 256;
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
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AHELog", "Check1");
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                Log.d("AHELog", "Check2");
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    File f = new File(imageUri.getPath());
                    String imagePath = f.getAbsolutePath();

                    Bitmap bitmap;
                    Log.d("AHELog", "Check3");

                    //InputStream image_stream = getContentResolver().openInputStream(imageUri);
                    Log.d("AHELog", "Check4");
                    bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath), LENGTH, LENGTH);
                    Log.d("AHELog", "Check5");
                    //if (bitmap.getByteCount() >= IMAGE_MAX_BYTES);
                    Log.d("AHELog", "Check6");
                    eventImageView.setImageBitmap(bitmap);
                    Log.d("AHELog", "Check7");
                    // Thumbnails
                    // Taken from: http://www.rogerethomas.com/blog/generating-square-cropped-thumbnails-in-android-java
                    // on Nov 25, 2017
                }
        }
    }
}
