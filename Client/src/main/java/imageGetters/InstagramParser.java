package imageGetters;

import java.io.InputStreamReader;
import model.Picture;
import com.google.gson.*;

public class InstagramParser {

    private JsonArray jsonPictures;
    JsonObject obj;

    public JsonArray parse(InputStreamReader reader) {
        JsonParser parser = new JsonParser();
        obj = parser.parse(reader).getAsJsonObject();
        jsonPictures = obj.get("data").getAsJsonArray();
        return jsonPictures;
    }

    public String getNextUrl() {
        JsonElement next_url = obj.get("pagination");
        String url = next_url.getAsJsonObject().get("next_url").getAsString();
        return url;
    }

    public Picture addToList(JsonElement j) {
        JsonObject jsonPicture = j.getAsJsonObject();

        

        JsonElement images = jsonPicture.get("images");
        JsonElement thumbImage = images.getAsJsonObject().get("thumbnail"); //thumbnail=150*150/standard_resolution=612*612
        String thumbUrl = thumbImage.getAsJsonObject().get("url").getAsString();

        JsonElement largeImage = images.getAsJsonObject().get("standard_resolution");
        String largeUrl = largeImage.getAsJsonObject().get("url").getAsString();

        Picture picture = new Picture(largeUrl, thumbUrl);

        return picture;
    }
}
