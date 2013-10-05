package uis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InstagramGetter {
	
	//This is a test to see if github works.

	private String instagramUrl = "https://api.instagram.com/v1/tags/nofilter/media/recent?client_id=CLIENTID";
	
	public List<Picture> getPictures() throws IOException {
		URL url = new URL(instagramUrl);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
				
		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		
		InstagramParser parser = new InstagramParser();
		List<Picture> pictures = parser.parse(reader);
		
		return pictures;
	}

}
