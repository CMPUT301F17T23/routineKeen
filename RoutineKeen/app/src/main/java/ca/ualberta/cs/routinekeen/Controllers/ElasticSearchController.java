package ca.ualberta.cs.routinekeen.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.User;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by hdc on 11/4/17.
 */

public class ElasticSearchController {
    private static JestDroidClient client;
    private static final String ELASTICSEARCH_URL = "http://cmput301.softwareprocess.es:8080";
    private static final String INDEX_NAME = "cmput301f17t23_routinekeen";

    public static class AddUserTask extends AsyncTask<User, Void, User> {
        @Override
        protected User doInBackground(User... users) {
            verifySettings();

            // Enforce adding only one user with this async task
            if (users.length > 1)
                throw new RuntimeException("Illegal Task Call: One user at a time.");

            User user = users[0];
            Index index = new Index.Builder(user).index(INDEX_NAME).type("user").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()){
                    user.setUserID(result.getId());
                }
                else {
                    Log.i("Error", "Elastic search was not able to add the user.");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the users.");
            }
            return user;
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

    public static void verifySettings() {
        if (client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(ELASTICSEARCH_URL);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
