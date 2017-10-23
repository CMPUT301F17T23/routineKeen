package ca.ualberta.cs.routinekeen.Models;

/**
 * Created by hughc on 2017-10-23.
 */

public class Photo {
    private byte[] photo;

    public Photo(byte[] photo){
        this.photo = photo;
    }

    public byte[] getPhoto(){
        return photo;
    }

    public void setPhoto(byte[] photo){
        this.photo = photo;
    }
}
