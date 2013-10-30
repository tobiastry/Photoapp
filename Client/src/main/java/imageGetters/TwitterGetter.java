package imageGetters;

import getImage.UpdateThread;
import imageGetters.TwitterParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TwitterGetter {
	private  UpdateThread thread;
	private  TwitterParser parser;
	
	public TwitterGetter(UpdateThread thread) {
		this.thread=thread;
	}

	private String twitterUrl(String tag){
		String twitterUrl = "https://api.twitpic.com/2/tags/show.json?tag="+tag; //client_id=af7c41b64a2419a8dfe31897f74f7fa2" (Only needed for uploading)
		return twitterUrl;
	}

	public void getPictures(String tag) throws IOException {
		URL url = new URL(twitterUrl(tag));
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		parser = new TwitterParser(thread);
		parser.parse(reader);
	}
	
	public void addToList(){
		parser.addToList();
	}

}
