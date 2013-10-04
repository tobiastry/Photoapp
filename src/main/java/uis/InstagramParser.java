package uis;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.*;

public class InstagramParser {

	public List<Picture> parse(InputStreamReader reader) {
		// TODO Auto-generated method stub
		ArrayList<Picture> pictures = new ArrayList<Picture>();
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(reader).getAsJsonObject();
		JsonArray jsonPictures = obj.get("data").getAsJsonArray();
		
		for(JsonElement j : jsonPictures) {
			Picture picture = new Picture();
			
			JsonObject jsonPicture = j.getAsJsonObject();
			JsonElement images = jsonPicture.get("images");
			JsonElement image = images.getAsJsonObject().get("standard_resolution");
			String url = image.getAsJsonObject().get("url").getAsString();
			picture.url = url;
			
			
			JsonElement caption = jsonPicture.get("caption");
			String text = caption.getAsJsonObject().get("text").getAsString();
			picture.description = text;
			
			
			
			pictures.add(picture);
		}
		
		
		return pictures;
	}

}
