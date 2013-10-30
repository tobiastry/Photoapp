package imageGetters;

import java.io.InputStreamReader;

import com.google.gson.*;
import model.Picture;
import getImage.UpdateThread;

public class TwitterParser {
	private  UpdateThread thread;
	private  JsonArray jsonPictures;

	public TwitterParser(UpdateThread thread) {
		this.thread=thread;
	}

	public void parse(InputStreamReader reader) {
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(reader).getAsJsonObject();
		jsonPictures = obj.get("images").getAsJsonArray();
		thread.setSize(jsonPictures.size());

		

	}

	public void addToList(){
		if(jsonPictures.size()!=0){
			for(JsonElement j : jsonPictures) {
				Picture picture = new Picture();
				JsonObject jsonPicture = j.getAsJsonObject();
				String images = jsonPicture.get("id").getAsString();
				picture.thumbUrl = "http://d3j5vwomefv46c.cloudfront.net/photos/thumb/" + images + ".jpg"; //thumb=150*150/large=?
				picture.largeUrl = "http://d3j5vwomefv46c.cloudfront.net/photos/large/" + images + ".jpg";

				thread.add(picture);
				thread.addCount();
			}
		}
	}
}
