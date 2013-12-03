package repository;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Stian
 */
public class TagCom {
    private String delayUrl = "http://pensolut.com:8084/api/tag";

    /*public ArrayList<String> getTags() throws IOException {
        
    }*/

    public int storeTag(String tag) throws IOException {
        URL url = new URL(delayUrl + "/addtag");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/v2+json");
        connection.setDoOutput(true);
        
        connection.connect();

        DataOutputStream outStream;
        outStream = new DataOutputStream(connection.getOutputStream());
        outStream.writeBytes(tag);
        outStream.flush();
        outStream.close();
        
        return connection.getResponseCode();
    }
}
