package ca.ualberta.cs.routinekeen.Models;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

/**
 * Created by hughc on 2017-11-05.
 */

public class UserList extends Observable {
    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<User>();
    }

    public UserList(ArrayList<User> userList) {
        this.users = userList;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
        setChanged();
        notifyObservers();
    }

    public int userListSize(){
        return users.size();
    }
}
