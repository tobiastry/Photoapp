package sletting;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

/**
 *
 * @author Emil
 */
public class PictureCom {
    
    private String getPictureURL = "http://pensolut.com:8084/api/picture/getpictures";
    private Strinv deletePictureURL = "http://pensolut.com:8084/api/delay";

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