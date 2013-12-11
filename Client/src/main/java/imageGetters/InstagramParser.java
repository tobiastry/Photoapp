package imageGetters;

import model.Picture;
import com.google.gson.*;

/**
 *
 * @author T
 */
public class InstagramParser {

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
