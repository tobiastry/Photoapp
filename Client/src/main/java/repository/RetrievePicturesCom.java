package repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

    String request = "http://pensolut.com:8084/api/picture/getpictures";

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
        char[] hei = new char[512];
        reader.read(hei);
        System.out.println(hei[0]+hei[1]+hei[2]+hei[3]);
        //if (reader.toString().startsWith("{")) {

            JsonParser parser = new JsonParser();
//            JsonObject obj = parser.parse(reader).getAsJsonObject();
 //           JsonArray imageUrlArray = obj.get("url").getAsJsonArray();
            
            JsonArray imageUrlArray = parser.parse(reader).getAsJsonArray();
            System.out.println(imageUrlArray.size());

            for (JsonElement j : imageUrlArray) {
                String imageUrl = j.getAsJsonObject().get("url").getAsString();
                System.out.println(imageUrl);
                imageList.add(imageUrl);
            }
            System.out.println("HTTP respons: " + connection.getResponseCode());

    //    }

        return imageList;
    }
}

class Mainer {

    public static void main(String[] args) {
        RetrievePicturesCom com = new RetrievePicturesCom();

        try {
            System.out.println(com.getImageList().get(0));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}