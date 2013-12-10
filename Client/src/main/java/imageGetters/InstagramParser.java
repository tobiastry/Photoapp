package imageGetters;

import java.io.InputStreamReader;
import model.Picture;
import com.google.gson.*;

/**
 *
 * @author T
 */
public class InstagramParser {

    private JsonArray jsonPictures;
    JsonObject obj;

    /**
     * Finds the location of the pictures in the InputStreamReader, returns them
     * as a JsonArray.
     *
     * @param reader (InputStreamReader)
     * @return jsonPictures (JsonArray)
     */
    public JsonArray parse(InputStreamReader reader) {
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
        JsonElement pagination = obj.get("pagination");
        if (pagination != null) {
            try {
                String url = pagination.getAsJsonObject().get("next_url").getAsString();
                if (url != null) {
                    return url;
                } else {
                    return null;
                }
            } catch (NullPointerException e) {
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
            try {
                JsonElement min_tag_id = pagination.getAsJsonObject().get("min_tag_id");
                if (min_tag_id != null) {
                    String minTagID = min_tag_id.getAsString();
                    return minTagID;
                }
                else{ 
                    return "0";                
                }
            } catch (NullPointerException e) {
                return "0";
            }
        } else {
            return "0";
        }
    }

    /**
     * Finds the URLs in the JsonElement and makes a type Picture out of them.
     *
     * @param j (JsonElement)
     * @return picture (model.Picture)
     */
    public Picture addToList(JsonElement j) {
        JsonObject jsonPicture = j.getAsJsonObject();

        JsonElement images = jsonPicture.get("images");
        JsonElement thumbImage = images.getAsJsonObject().get("thumbnail"); //thumbnail=150*150/standard_resolution=612*612
        String thumbUrl = thumbImage.getAsJsonObject().get("url").getAsString();

        JsonElement largeImage = images.getAsJsonObject().get("standard_resolution");
        String largeUrl = largeImage.getAsJsonObject().get("url").getAsString();

        Picture picture = new Picture(largeUrl, thumbUrl);

        String id = jsonPicture.get("id").getAsString();
        picture.setId(id);
        String unixDate = jsonPicture.get("created_time").getAsString();
        picture.setUnixDate(unixDate);
        return picture;
    }
}
