package ca.ualberta.cs.routinekeen.Controllers;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import ca.ualberta.cs.routinekeen.Models.User;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;

/**
 * Created by hdc on 11/4/17.
 */

public class ElasticSearchController {
    private static JestDroidClient client;
    private static final String ELASTICSEARCH_URL = "http://cmput301.softwareprocess.es:8080";
    private static final String INDEX_NAME = "cmput301f17t23_routinekeen";

    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            return null;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... strings) {
            return null;
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
