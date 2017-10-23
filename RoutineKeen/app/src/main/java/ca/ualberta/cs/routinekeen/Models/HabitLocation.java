package ca.ualberta.cs.routinekeen.Models;

/**
 * Created by hughc on 2017-10-23.
 */
import android.location.Address;
import com.google.android.gms.maps.model.LatLng;

public class HabitLocation {
    private String locationName;
    private LatLng geoCoords;
    private Address address;

    public HabitLocation(String location) {
        this.locationName = location;
    }

    public HabitLocation(String locationName, Address address) {
        this.locationName = locationName;
        this.address = address;
    }

    public HabitLocation(String location, LatLng geoCoords, Address address) {
        this.locationName = locationName;
        this.geoCoords = geoCoords;
        this.address = address;
    }

    public String getLocation() {
        return locationName;
    }

    public void setLocation(String location) {
        this.locationName = locationName;
    }

    public LatLng getGeoCoords() {
        return geoCoords;
    }

    public void setGeoCoords(LatLng geoCoords) {
        this.geoCoords = geoCoords;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
