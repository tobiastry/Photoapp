/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageGetters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import model.Picture;

/**
 *
 * @author John McEpic
 */
public class TwitterParser {

    private JsonArray jsonPictures;
    JsonObject obj;

    public JsonArray parse(InputStreamReader reader) {
        JsonParser parser = new JsonParser();
        obj = parser.parse(reader).getAsJsonObject();
        jsonPictures = obj.get("statuses").getAsJsonArray();
        jsonPictures = findMedia(jsonPictures);
        return jsonPictures;
    }

    private JsonArray findMedia(JsonArray jsonPictures) {
        JsonArray test = new JsonArray();
        for (int i = 0; i < jsonPictures.size(); i++) {
            JsonObject entities = (JsonObject) jsonPictures.get(i).getAsJsonObject().get("entities");
            JsonElement media = entities.getAsJsonObject().get("media");
            if (media != null) {
                JsonElement mediashave = ((JsonArray) media).get(0);
                test.add(mediashave);
            }
        }
        return test;
    }

    public String getNextUrl() {
        JsonElement next_url = obj.get("pagination");
        String url = next_url.getAsJsonObject().get("next_url").getAsString();
        System.out.println(url);
        return url;
    }

    public Picture addToList(JsonElement j) {
        JsonObject jsonPicture = j.getAsJsonObject();
        Picture picture = new Picture();
        String url = jsonPicture.getAsJsonObject().get("media_url").getAsString();
        picture.thumbUrl = url + ":thumb";
        picture.largeUrl = url + ":large";

        return picture;

    }
}
