package ca.ualberta.cs.routinekeen.Controllers;

import ca.ualberta.cs.routinekeen.Models.User;

/**
 * Created by hdc on 11/6/17.
 * created sperate branch on 11/8/17
 */

public class NetworkDataManager {
    public static void AddNewUser(User user){
        ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
        addUserTask.execute(user);
    }

    public static void GetUser(User user){
        ElasticSearchController.GetUserTask getUserTask = new ElasticSearchController.GetUserTask();
    }
}
