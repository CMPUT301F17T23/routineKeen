package ca.ualberta.cs.routinekeen.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.User;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Controller to issue CRUD operations to elastic search. Allows user
 * of this class to issue commands such as: add habits, add users,
 * add habit events, etc.
 *
 * @author Hugh Craig
 * @see NetworkDataManager
 * @version 1.2.0
 */

public class ElasticSearchController {
    private static JestDroidClient client;
    private static final String ELASTICSEARCH_URL = "http://cmput301.softwareprocess.es:8080";
    private static final String INDEX_NAME = "cmput301f17t23_routinekeen";

    public static class AddUserTask extends AsyncTask<User, Void, String> {
        @Override
        protected String doInBackground(User... users) {
            verifySettings();

            // Enforce adding only one user with this async task
            if (users.length > 1)
                throw new RuntimeException("Illegal Task Call: One user at a time.");

            String assignedUserID = null;
            Index index = new Index.Builder(users[0]).index(INDEX_NAME).type("user").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()){
                    assignedUserID = result.getId().toString();
                } else {
                    Log.i("Error", "Elastic search was not able to add the user.");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the users.");
            }
            return assignedUserID;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... search_parameters) {
            verifySettings();
            User userResult = null;
            String username = search_parameters[0];
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "           \"default_field\" : \"username\",\n" +
                    "               \"query\" : \"" + username + "\"\n" +
                    "               }\n" +
                    "           }\n" +
                    "       }";
            Search search = new Search.Builder(query)
                                    .addIndex(INDEX_NAME)
                                    .addType("user")
                                    .build();
            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    userResult = result.getSourceAsObject(User.class);
                }
            } catch(Exception e){
                Log.i("Error", "Something went wrong when we tried to communicate with the elastic search server!");
            }

            return userResult;
        }
    }

    public static class AddHabitTask extends AsyncTask<Habit, Void, String> {
        @Override
        protected String doInBackground(Habit... habits){
            verifySettings();

            // Enforce adding only one user with this async task
            if (habits.length > 1)
                throw new RuntimeException("Illegal Task Call: One habit at a time.");

            String assignedHabitID = null;
            Index index = new Index.Builder(habits[0]).index(INDEX_NAME).type("habit").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    assignedHabitID = result.getId().toString();
                } else {
                    Log.i("Error", "Elastic search was not able to add the habit.");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the users.");
            }

            return assignedHabitID;
        }
    }

    public static class DeleteHabitByTitleTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... habitTypes) {
            verifySettings();

            String habitType = habitTypes[0];
            String query = "{\n" +
                    "   \"query\": {\n" +
                    "       \"term\": {\n" +
                    "           \"habitTitle\": \"" + habitType + "\"\n" +
                    "           }\n" +
                    "       }\n" +
                    "   }";
            JestResult result = null;
            DeleteByQuery delete = new DeleteByQuery.Builder(query)
                                        .addIndex(INDEX_NAME)
                                        .addType("habit")
                                        .build();
            try{
                result = client.execute(delete);
            } catch (Exception e){
                Log.i("Error", "Something went wrong when we tried to communicate with the elastic search server!");
            }

            return  result.isSucceeded() ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    public static class GetUserHabitsTask extends AsyncTask<String, Void, ArrayList<Habit>> {
        @Override
        protected ArrayList<Habit> doInBackground(String... user_ids) {
            verifySettings();

            if(user_ids.length > 1){
                throw new RuntimeException("Only one User ID is expected for task.");
            }

            ArrayList<Habit> habitsResult = new ArrayList<Habit>();
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "           \"default_field\" : \"associatedUserID\",\n" +
                    "               \"query\" : \"" + user_ids[0] + "\"\n" +
                    "               }\n" +
                    "           }\n" +
                    "       }";
            Search search = new Search.Builder(query)
                    .addIndex(INDEX_NAME)
                    .addType("habit")
                    .build();

            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    List<Habit> foundHabitEvents = result.getSourceAsObjectList(Habit.class);
                    habitsResult.addAll(foundHabitEvents);
                }
            } catch (IOException e){
                Log.i("Error", "Something went wrong when we tried to communicate with the elastic search server!");
            }

            return habitsResult;
        }
    }

    public static class GetHabitByTitleTask extends AsyncTask<String, Void, Habit> {
        @Override
        protected Habit doInBackground(String... search_parameters) {
            verifySettings();

            Habit habitResult = null;
            String habitTitle = search_parameters[0];
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "           \"default_field\" : \"habitTitle\",\n" +
                    "               \"query\" : \"" +  habitTitle + "\"\n" +
                    "               }\n" +
                    "           }\n" +
                    "       }";
            Search search = new Search.Builder(query)
                    .addIndex(INDEX_NAME)
                    .addType("habit")
                    .build();
            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    habitResult = result.getFirstHit(Habit.class).source;
                    habitResult.setHabitID(result.getFirstHit(Habit.class).id);
                }
            } catch(Exception e){
                Log.i("Error", "Something went wrong when we tried to communicate with the elastic search server!");
            }

            return habitResult;
        }
    }

    public static class UpdateHabitTask extends AsyncTask<Habit, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Habit... habits) {
            verifySettings();

            if(habits.length > 1)
                throw new RuntimeException("Illegal Task Call. One habit at a time.");

            Index index = new Index.Builder(habits[0])
                                .index(INDEX_NAME)
                                .type("habit")
                                .id(habits[0].getHabitID())
                                .build();
            JestResult result = null;
            try{
                result = client.execute(index);
            } catch(Exception e){
                Log.i("Error", "The application failed to update the habit.");
            }

            return result.isSucceeded() ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    public static class GetHabitIdTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... habitType) {
            verifySettings();

            if(habitType.length > 1){
                throw new RuntimeException("Illegal task call. One habit type at a time.");
            }

            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "           \"default_field\" : \"habitTitle\",\n" +
                    "               \"query\" : \"" +  habitType[0] + "\"\n" +
                    "               }\n" +
                    "           }\n" +
                    "       }";
            Search search = new Search.Builder(query)
                    .addIndex(INDEX_NAME)
                    .addType("habit")
                    .build();
            String retrievedHabitId = null;
            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded())
                    retrievedHabitId = result.getFirstHit(Habit.class).id.toString();
            } catch (Exception e){
                Log.i("Error", "Something went wrong when we tried to communicate with the elastic search server!");
            }

            return retrievedHabitId;
        }
    }

    public static class AddHabitEventTask extends AsyncTask<HabitEvent, Void, String> {
        @Override
        protected String doInBackground(HabitEvent... habitEvents) {
            verifySettings();

            if(habitEvents.length > 1)
                throw new RuntimeException("Illegal Task Call: One habit event at a time.");

            String assignedEventID = null;
            Index index = new Index.Builder(habitEvents[0]).index(INDEX_NAME).type("habitEvent").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()){
                    assignedEventID = result.getId().toString();
                } else {
                    Log.i("Error", "Elastic search was not able to add the habit event.");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the habit event.");
            }

            return assignedEventID;
        }
    }

    public static class GetUserHabitEventsTask extends AsyncTask<String, Void, ArrayList<HabitEvent>> {
        @Override
        protected ArrayList<HabitEvent> doInBackground(String... user_ids) {
            verifySettings();

            if (user_ids.length > 1) {
                throw new RuntimeException("Only one User ID is expected for task.");
            }

            ArrayList<HabitEvent> habitEventsResult = null;
            String userID = user_ids[0];
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"query_string\" : {\n" +
                    "           \"default_field\" : \"associatedUserID\",\n" +
                    "               \"query\" : \"" + userID + "\"\n" +
                    "               }\n" +
                    "           }\n" +
                    "       }";
            Search search = new Search.Builder(query)
                    .addIndex(INDEX_NAME)
                    .addType("habitEvent")
                    .build();

            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    List<HabitEvent> foundHabitEvents = result.getSourceAsObjectList(HabitEvent.class);
                    habitEventsResult.addAll(foundHabitEvents);
                }
            } catch (Exception e){
                Log.i("Error", "Something went wrong when we tried to communicate with the elastic search server!");
            }

            return habitEventsResult;
        }
    }

    private static void verifySettings() {
        if (client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(ELASTICSEARCH_URL);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
