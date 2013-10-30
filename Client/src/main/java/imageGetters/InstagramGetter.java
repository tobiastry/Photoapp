package imageGetters;

import getImage.UpdateThread;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InstagramGetter {
	private  UpdateThread thread;
	private  InstagramParser parser;
	
	public InstagramGetter(UpdateThread thread) {
		this.thread=thread;
	}

	private String instagramUrl(String tag){
//		boolean isAscii = CharMatcher.ASCII.matchesAllOf(tag);
//		if
		String instagramUrl = "https://api.instagram.com/v1/tags/"+tag+"/media/recent?client_id=27dbaa9b6235400f8dc76af4aa5b0458";
		return instagramUrl;
	}

	public void getPictures(String tag) throws IOException {
		URL url = new URL(instagramUrl(tag));
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		parser = new InstagramParser(thread);
		parser.parse(reader);

	}
	
	public void addToList(){
		parser.addToList();
	}

}
