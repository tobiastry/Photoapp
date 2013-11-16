package imageGetters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import model.Picture;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class InstagramGetter {

    private InstagramParser parser;
    private JsonArray jsonPictures;

    public String toUrl(String tag) {
        if (Pattern.matches("[a-zA-Z]+", tag)) {
            String instagramUrl = "https://api.instagram.com/v1/tags/" + tag + "/media/recent?client_id=27dbaa9b6235400f8dc76af4aa5b0458";
            return instagramUrl;
        } else {
            return null;
        }
    }

    public JsonArray findPictures(String surl) throws IOException {
        URL url = new URL(surl);
        System.out.println(url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        parser = new InstagramParser();
        jsonPictures = parser.parse(reader);

        return jsonPictures;

    }

    public String getNextUrl() {
        return parser.getNextUrl();
    }

    public Picture addToList(JsonElement j) {
        Picture picture = parser.addToList(j);
        return picture;
    }
}
