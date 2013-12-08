/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageGetters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import model.Picture;
import repository.AuthenticationTwitter;

/**
 *
 * @author T
 */
public class TwitterGetter {

    private TwitterParser parser;
    private JsonArray jsonPictures;

    /**
     * Takes a tag and makes a valid URL out of it.
     * @param tag
     * @return
     */
    public String toUrl(String tag) {
        if (Pattern.matches("[\\wÆØÅæøå]+", tag)) {
            String TwitterUrl = "https://api.twitter.com/1.1/search/tweets.json?q=%23" + tag + "&result_type=recent&count=100";
            return TwitterUrl;
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
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "Photoappdat210");
            connection.setRequestProperty("Authorization", "Bearer " + requestBearerToken());
            connection.setUseCaches(false);
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            parser = new TwitterParser();
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

    public Picture addToList(JsonElement j) {
        Picture picture = parser.addToList(j);
        return picture;
    }

    private String requestBearerToken() {
        try {
            AuthenticationTwitter auth = new AuthenticationTwitter();
            return auth.requestBearerToken();
        } catch (IOException ex) {
            Logger.getLogger(TwitterGetter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
