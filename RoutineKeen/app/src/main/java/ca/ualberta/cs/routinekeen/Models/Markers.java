package ca.ualberta.cs.routinekeen.Models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tiakindele on 2017-11-27.
 */

public class Markers {
    private final String id;
    private final String title;
    private final LatLng location;

    public Markers(String id, String title, LatLng location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public String getMarkerId() { return id; }
    public String getMarkerTitle() { return title; }
    public LatLng getMarkerLocation() { return location; }
}
