package repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import model.Picture;

/**
 *
 * @author Emil
 */
public class RetrievePicturesCom {

    private String request = "http://pensolut.com:8084/api/picture/getpictures";

    /**
     * Retrieves a list of Pictures from the Server
     * @return An Arraylist of Picture objects.
     * @throws IOException 
     */
    public ArrayList<Picture> getImageList() throws IOException {
        ArrayList<Picture> imageList = new ArrayList();

        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/v1+json");
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());

        int respons = connection.getResponseCode();
        
        if (respons == 200) {
            JsonParser parser = new JsonParser();

            JsonArray imageUrlArray = parser.parse(reader).getAsJsonArray();

            for (JsonElement j : imageUrlArray) {
                Picture picture = new Picture(j.getAsJsonObject().get("url").getAsString(), 
                        j.getAsJsonObject().get("thumburl").getAsString());
                imageList.add(picture);
                System.out.println(picture.getLargeUrl());
            }
        }
        return imageList;
    }
    
}
