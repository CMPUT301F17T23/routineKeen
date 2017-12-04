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

    public boolean containsUser(String username){
        for(User x: users){
            if(x.getUsername().equals(username))
                return true;
        }

        return false;
    }

    public User getUser(String username){
        for(User x: users){
            if(x.getUsername().equals(username))
                return x;
        }

        return null;
    }

    public boolean removeUser(String username){
        int position = -1;
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                position = i;
                break;
            }
        }

        if(position != -1) {
            users.remove(position);
            setChanged();
            notifyObservers(users);
            return true;
        }
        return false;
    }

    public void addUser(User user){
        users.add(user);
        setChanged();
        notifyObservers(users);
    }

    public int userListSize(){
        return users.size();
    }
}
