package imageGetters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author T
 */
public class TwitterGetter {

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
            String TwitterUrl = "https://api.twitter.com/1.1/search/tweets.json?q=%23" + tag + "&result_type=recent&count=100";
            return TwitterUrl;
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
    public JsonArray findPictures(String surl, String bearerToken) throws IOException {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(surl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "Photoappdat210");
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            connection.setUseCaches(false);
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
        jsonPictures = obj.get("statuses").getAsJsonArray();
        jsonPictures = findMedia(jsonPictures);
        return jsonPictures;
    }

    private JsonArray findMedia(JsonArray jsonPictures) {
        JsonArray tempArray = new JsonArray();
        for (int i = 0; i < jsonPictures.size(); i++) {
            JsonObject entities = (JsonObject) jsonPictures.get(i).getAsJsonObject().get("entities");
            if (entities != null && hasPictureUrl(entities)) {
                tempArray.add(jsonPictures.get(i).getAsJsonObject());
            }
        }
        return tempArray;
    }

    /**
     * Finds the next url in the InputStreamReader and returns it as a string.
     *
     * @return next_url (String)
     */
    public String getNextUrl() {
        JsonElement next_url = obj.get("search_metadata");
        if (next_url != null) {
            String url = next_url.getAsJsonObject().get("next_results").getAsString();
            if (url != null) {
                return "https://api.twitter.com/1.1/search/tweets.json" + url;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private boolean hasPictureUrl(JsonObject entities) {
        JsonElement media = entities.getAsJsonObject().get("media");
        JsonElement url = entities.getAsJsonObject().get("urls");
        if (media != null) {
            return true;
        }
        if (!"[]".equals(url.toString())) {
            url = ((JsonArray) url).get(0);
            if (url != null) {
                String pictureUrl = url.getAsJsonObject().get("expanded_url").getAsString();
                if (pictureUrl.contains("twitpic")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
