package imageGetters;

import java.io.InputStreamReader;
import model.Picture;
import com.google.gson.*;

public class TwitterParser {

    private JsonArray jsonPictures;
    JsonObject obj;

    public JsonArray parse(InputStreamReader reader) {
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(reader).getAsJsonObject();
        jsonPictures = obj.get("images").getAsJsonArray();
        return jsonPictures;
    }

    /*	public String getNextUrl() {
     JsonElement next_url = obj.get("pagination");
     String url = next_url.getAsJsonObject().get("next_url").getAsString();
     System.out.println(url);
     return url;
     }*/
    public Picture addToList(JsonElement j) {

        JsonObject jsonPicture = j.getAsJsonObject();
        String images = jsonPicture.get("id").getAsString();
        String thumbUrl = "http://d3j5vwomefv46c.cloudfront.net/photos/thumb/" + images + ".jpg"; //thumb=150*150/large=?
        String largeUrl = "http://d3j5vwomefv46c.cloudfront.net/photos/large/" + images + ".jpg";
        
        Picture picture = new Picture(largeUrl, thumbUrl);

        return picture;
    }
}
