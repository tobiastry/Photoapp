package imageGetters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author T
 */
public class InstagramGetter {

    private JsonArray jsonPictures;
    private JsonObject obj;

    /**
     * Takes a tag and makes a valid URL out of it.
     *
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
     * Sends a request to the site with the given URL receives a JSON reply
     * parses it and returns a JsonArray which contains the pictures.
     *
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
            jsonPictures = findJsonPictures(reader);

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

    /**
     * Finds the location of the pictures in the InputStreamReader, returns them
     * as a JsonArray.
     *
     * @param reader (InputStreamReader)
     * @return jsonPictures (JsonArray)
     */
    public JsonArray findJsonPictures(InputStreamReader reader) {
        JsonParser parser = new JsonParser();
        obj = parser.parse(reader).getAsJsonObject();
        jsonPictures = obj.get("data").getAsJsonArray();
        return jsonPictures;
    }

    /**
     * Finds the next url in the InputStreamReader and returns it as a string.
     *
     * @return next_url (String)
     */
    public String getNextUrl() {
        JsonElement next_url = obj.get("pagination");
        if (next_url != null) {
            String url = next_url.getAsJsonObject().get("next_url").getAsString();
            if (url != null) {
                return url;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    /**
     * Finds the the newest ID for the tag in the InputStreamReader and returns
     * it as a int.
     *
     * @return minTagID (int)
     */
    public String getMinID() {
        JsonElement pagination = obj.get("pagination");
        if (pagination != null) {
                JsonElement min_tag_id = pagination.getAsJsonObject().get("min_tag_id");
                if (min_tag_id != null) {
                    String minTagID = min_tag_id.getAsString();
                    return minTagID;
                } else {
                    return "0";
                }
        } else {
            return "0";
        }
    }
}
