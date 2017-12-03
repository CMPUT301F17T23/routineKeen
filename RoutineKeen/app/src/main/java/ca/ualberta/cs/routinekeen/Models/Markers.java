package ca.ualberta.cs.routinekeen.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tiakindele on 2017-11-27.
 */

public class Markers implements Parcelable{
    private final String id;
    private final String title;
    private final LatLng location;
//    private ArrayList<Markers> markersArrayList;

    public Markers(String id, String title, LatLng location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    protected Markers(Parcel in) {
        id = in.readString();
        title = in.readString();
        location = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<Markers> CREATOR = new Creator<Markers>() {
        @Override
        public Markers createFromParcel(Parcel in) {
            return new Markers(in);
        }

        @Override
        public Markers[] newArray(int size) {
            return new Markers[size];
        }
    };

    public String getMarkerId() { return id; }
    public String getMarkerTitle() { return title; }
    public LatLng getMarkerLocation() { return location; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeParcelable(location, i);
    }
}
