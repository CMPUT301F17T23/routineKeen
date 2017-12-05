package ca.ualberta.cs.routinekeen.Helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import org.apache.lucene.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Date;

/**
 * A set of helpers used to upload, save, and load HabitEvent photos
 *
 * @author Alex Ridgway
 * @see     ca.ualberta.cs.routinekeen.Models.HabitEvent
 * @see     ca.ualberta.cs.routinekeen.Views.AddHabitEvent
 * @see     ca.ualberta.cs.routinekeen.Views.ViewHabitEvent
 * @version 1.0.0
 */

public class PhotoHelpers extends ContextWrapper {

    private static final int MAX_FILESIZE = 65536;
    private static final int IMG_MAX_SIZE = (int) Math.floor(Math.sqrt(MAX_FILESIZE));

    public PhotoHelpers(Context base) {
        super(base);
    }
    // taken from https://itekblog.com/android-context-in-non-activity-class/
    // on Dec 2, 2017


    /**
     * Converts an InputStream containing an image to a byte array
     * @param imageStream An InputStream containing an image
     * @return The supplied image in byte[] from
     */

    public static byte[] convertImageStreamToByteArray(InputStream imageStream){
        // Taken from: https://stackoverflow.com/questions/10296734/image-uri-to-bytesarray
        // Date: December 1, 2017
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        try {
            while ((len = imageStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        } catch (IOException e){

        }

        return byteBuffer.toByteArray();
    }


    /**
     * Converts an InputStream containing an image to a bitmap. If the Stream is too big to load
     * into memory, the function returns null.
     * @param imageStream   An InputStream containing an image to be decoded
     * @return The supplied image in bitmap form, or null if decode fails
     */

    public static Bitmap convertImageStreamToBitMap(InputStream imageStream){
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(imageStream);
        } catch (OutOfMemoryError e){
            return null;
        }
        return image;
    }


    /**
     * Takes an InputStream containing an image and scales it down to a bitmap thumbnail with
     * supplied dimensions.
     * @param imageStream   An InputStream containing an image to be decoded and scaled
     * @param width         Width for output thumbnail
     * @param height        Height for output thumbnail
     * @return      A bitmap thumbnail with dimensions width*height
     */

    public static Bitmap convertImageStreamToThumbnail(InputStream imageStream, int width, int height){

        Bitmap selectedImage = null;
        try {
            selectedImage = BitmapFactory.decodeStream(imageStream);
        } catch (OutOfMemoryError e) {
            System.gc();
            try {
                selectedImage = BitmapFactory.decodeStream(imageStream);
            } catch (OutOfMemoryError e2) {
                return null;
            }
        }
        // Taken from https://stackoverflow.com/questions/7138645/catching-outofmemoryerror-in-decoding-bitmap
        // on Dec 2, 2017


        // convert selected image to thumbnail smaller than IMAGE_MAX_BYTES
        Bitmap thumbImage = ThumbnailUtils.extractThumbnail(selectedImage,
                width, height);
        // Taken from: http://www.rogerethomas.com/blog/generating-square-cropped-thumbnails-in-android-java
        // on Nov 25, 2017

        return thumbImage;
    }


    /**
     * Compresses a bitmap image until it is below a set number of bytes
     * @param imageBmp  The bitmap to be compressed
     * @return  The compressed bitmap
     */

    public byte[] compressImage (Bitmap imageBmp) {
        File destFile = new File(this.getFilesDir().getAbsolutePath(),"tempDestFile.png");
        int quality = 100;
        byte[] returnArray = null;
        InputStream is = null;

        while (true) {

            try {
                FileOutputStream out = new FileOutputStream(destFile);
                imageBmp.compress(Bitmap.CompressFormat.JPEG, quality, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                is = getContentResolver().openInputStream(Uri.fromFile(destFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            returnArray = convertImageStreamToByteArray(is);

            Log.d("AHELog", "Compressed image to "+String.valueOf(returnArray.length)+" size byte array. ("+String.valueOf(quality)+"% quality)");
            if (returnArray.length < MAX_FILESIZE) {
                break;
            } else {
                quality -= 5;
            }
        }

        return returnArray;
    }

}
