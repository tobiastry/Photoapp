package imageGetters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import model.Picture;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class TwitterGetter {
	private  TwitterParser parser;
	private  JsonArray jsonPictures;
//	int size;
	public TwitterGetter() {
		// TODO Auto-generated constructor stub
	}

	private String twittterUrl(String tag){
		if(Pattern.matches("[a-zA-Z]+", tag)){ 
			String twitterUrl = "https://api.twitpic.com/2/tags/show.json?tag="+tag; //client_id=af7c41b64a2419a8dfe31897f74f7fa2" (Only needed for uploading)
                        return twitterUrl;
		} 
		else{ 
			return null; 
		} 
	}

	public JsonArray findPictures(String tag) throws IOException {
		URL url = new URL(twittterUrl(tag));
		findMorePictures(url);
//		System.out.println(jsonPictures);
		return jsonPictures;

	}

	public JsonArray findMorePictures(URL url) throws IOException{
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
                System.out.println(connection);

		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                System.out.println(reader);

		parser = new TwitterParser();
		jsonPictures = parser.parse(reader);
//		size+=jsonPictures.size();
//		if(size<50){
//			URL next_url = new URL(parser.getNextUrl());
//			findMorePictures(next_url);
//			return jsonPictures;
//
//		}
		return jsonPictures;

	}

	public Picture addToList(JsonElement j){
		Picture picture = parser.addToList(j);
		return picture;
	}

}
