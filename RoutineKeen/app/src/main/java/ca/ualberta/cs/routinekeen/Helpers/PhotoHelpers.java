package ca.ualberta.cs.routinekeen.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import org.apache.lucene.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

/**
 * Created by hughc on 2017-12-01.
 */

public class PhotoHelpers {
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

    public static Bitmap convertImageStreamToBitMap(InputStream imageStream, int width, int height){
        // Taken from: http://www.rogerethomas.com/blog/generating-square-cropped-thumbnails-in-android-java
        // on Nov 25, 2017
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        // convert selected image to thumbnail smaller than IMAGE_MAX_BYTES
        Bitmap thumbImage = ThumbnailUtils.extractThumbnail(selectedImage,
                width, height);
        return thumbImage;
    }
}
