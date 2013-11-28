package repository;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Emil
 */
public class DeletePicturesCom {
    
    private String request = "http://pensolut.com:8084/api/picture/delete";
    private DataOutputStream outStream;
    
    /*
     * Takes an ArrayList with picture urls as strings, and sends it in a JSON
     * format to the server, for deletion.
     * Returns the HTTP respons code
     */
    public int deletePictures(ArrayList<String> imageList) throws IOException{
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/v1+json");
        connection.setDoOutput(true);
        connection.connect();
        
        String body = "[";
        
        outStream = new DataOutputStream(connection.getOutputStream());
        for(int i=0; i<imageList.size();i++){
            if(i!=imageList.size()-1)
                body = body+" {\"url\": "+"\""+imageList.get(i)+"\"},";
            else
                 body = body+" {\"url\": "+"\""+imageList.get(i)+"\"";
        }
        body = body+"}]";
        body = body.replace("thumb", "large");
        outStream.writeBytes(body);
        outStream.flush();
        outStream.close();
        /*System.out.println(body);
        System.out.println(connection.getResponseCode();*/
        return connection.getResponseCode();
    }
    
}