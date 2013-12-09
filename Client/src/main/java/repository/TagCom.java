package repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Stian
 */
public class TagCom {
    private String tagUrl = "http://pensolut.com:8084/api/tag";

    
    /**
     * Retrieves a list of tags from the server and stores them as strings in 
     * an ArrayList
     * @return ArrayList of the tags
     * @throws IOException 
     */
    public ArrayList<String> getTags() throws IOException {
        ArrayList<String> tagList = new ArrayList();

        URL url = new URL(tagUrl + "/gettags");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/v2+json");
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());

        int response = connection.getResponseCode();

        if (response == 200) {
            JsonParser parser = new JsonParser();
            JsonArray tagArray = parser.parse(reader).getAsJsonArray();

            for (JsonElement j : tagArray) {
                String tag = j.getAsJsonObject().get("name").getAsString();
                tagList.add(tag);
            }
        }
        return tagList;
    }

    /**
     * Stores the given tag on the server
     * @param tag
     * @return The response code from the server.
     * @throws IOException 
     */
    public int storeTag(String tag) throws IOException {
        URL url = new URL(tagUrl + "/addtag");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/v2+json");
        connection.setDoOutput(true);
        
        connection.connect();

        DataOutputStream outStream;
        outStream = new DataOutputStream(connection.getOutputStream());
        outStream.writeBytes("{\"name\": "+"\""+ tag +"\"}");
        outStream.flush();
        outStream.close();
        
        return connection.getResponseCode();
    }
    
    /**
     * Sends an ArrayList of tags to the server for removal
     * @param tags
     * @return The response code from the server.
     * @throws IOException 
     */
    public int removeTag(ArrayList<String> tags)throws IOException {
        if(tags.size() < 1){
            return 0;
        }
        URL url = new URL(tagUrl + "/delete");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/v2+json");
        connection.setDoOutput(true);
        connection.connect();
        
        String body = "[";
        
        DataOutputStream outStream;
        outStream = new DataOutputStream(connection.getOutputStream());
        for(int i=0; i<tags.size();i++){
            if(i!=tags.size()-1)
                body = body+" {\"name\": "+"\""+tags.get(i)+"\"},";
            else
                 body = body+" {\"name\": "+"\""+tags.get(i)+"\"";
        }
        body = body+"}]";
        System.out.println(body);
        outStream.writeBytes(body);
        outStream.flush();
        outStream.close();
        return connection.getResponseCode();
    }
}
