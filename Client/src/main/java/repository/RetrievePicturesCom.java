package repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Emil
 */
public class RetrievePicturesCom {

    private String request = "http://pensolut.com:8084/api/picture/getpictures";

    /**
     * Retrieving list of all the image URL's from the server
     */
    public ArrayList getImageList() throws IOException {
        ArrayList imageList = new ArrayList();
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
                String imageUrl = j.getAsJsonObject().get("thumburl").getAsString();
                imageList.add(imageUrl);
            }
        }
        return imageList;
    }
}

class Mainer {

    public static void main(String[] args) {
        RetrievePicturesCom com = new RetrievePicturesCom();

        try {
            ArrayList<String> list = com.getImageList();
            for (String url : list) {
                System.out.println(url);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}