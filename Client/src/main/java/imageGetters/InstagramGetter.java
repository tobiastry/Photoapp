package imageGetters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import model.Picture;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

/**
 * 
 * @author T
 */
public class InstagramGetter {

    private InstagramParser parser;
    private JsonArray jsonPictures;
    
    /**
     * Takes a tag and makes a valid URL out of it.
     * @param tag
     * @return
     */
    public String toUrl(String tag) {
        if (Pattern.matches("[\\wÆØÅæøå]+", tag)) {
            String instagramUrl = "https://api.instagram.com/v1/tags/" + tag + "/media/recent?client_id=27dbaa9b6235400f8dc76af4aa5b0458";
            return instagramUrl;
        } else {
            return null;
        }
    }
    
     /**
     * Sends a request to the site with the given URL receives a JSON reply parses it
     * and returns a JsonArray which contains the pictures.
     * @param surl (URL(String))
     * @return jsonPictures (JsonArray)
     * @throws IOException
     */
    public JsonArray findPictures(String surl) throws IOException {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(surl);
            connection = (HttpsURLConnection) (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            parser = new InstagramParser();
            jsonPictures = parser.parse(reader);

            if (jsonPictures != null) {
                return jsonPictures;
            }
        } catch (MalformedURLException e) {
            throw new IOException("Invalid URL specified.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public String getNextUrl() {
        return parser.getNextUrl();
    }
    
    public String getMinID() {
        return parser.getMinID();
    }

    public Picture addToList(JsonElement j) {
        Picture picture = parser.addToList(j);
        return picture;
    }
}
