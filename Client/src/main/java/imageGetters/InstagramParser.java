package imageGetters;

import java.io.InputStreamReader;
import com.google.gson.*;
import model.Picture;
import getImage.UpdateThread;

public class InstagramParser {
	private  UpdateThread thread;
	private  JsonArray jsonPictures;
	
	public InstagramParser(UpdateThread thread) {
		this.thread=thread;
	}

	public void parse(InputStreamReader reader) {
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(reader).getAsJsonObject();
		jsonPictures = obj.get("data").getAsJsonArray();
		thread.setSize(jsonPictures.size());
		
	}
	
	public void addToList(){
		if(jsonPictures.size()!=0){
			for(JsonElement j : jsonPictures) {
				JsonObject jsonPicture = j.getAsJsonObject();

				Picture picture = new Picture();

				JsonElement images = jsonPicture.get("images");
				JsonElement thumbImage = images.getAsJsonObject().get("thumbnail"); //thumbnail=150*150/standard_resolution=612*612
				String url = thumbImage.getAsJsonObject().get("url").getAsString();
				picture.thumbUrl = url;

				JsonElement largeImage = images.getAsJsonObject().get("standard_resolution");
				String largeUrl = largeImage.getAsJsonObject().get("url").getAsString();
				picture.largeUrl = largeUrl;
				thread.add(picture);
				thread.addCount();
			}
		}
	}
}
