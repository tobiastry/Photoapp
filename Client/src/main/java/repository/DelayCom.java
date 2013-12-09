package repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;

/**
 *
 * @author Stian
 */
public class DelayCom {

    private String delayUrl = GlobalVariables.baseUrl+"delay";

    /**
     * Retrieves the delay from the server
     * @return The Delay as an int
     * @throws IOException 
     */
    public int getDelay() throws IOException {
        URL url = new URL(delayUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/v1+json");
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(reader).getAsJsonObject();
        int delay = obj.get("time").getAsInt();

        return delay;
    }
    
    /**
     * Sets the Delay at the server
     * @param delay
     * @return The Delay set if successful
     * @throws IOException 
     */
    public int setDelay(int delay) throws IOException {
        URL url = new URL(delayUrl + "/" + delay);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/v1+json");
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(reader).getAsJsonObject();
        int response = obj.get("time").getAsInt();

        return response;
    }
}
