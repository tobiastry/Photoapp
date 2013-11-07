/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import model.Picture;

/**
 *
 * @author Emil
 */
public class StorePicturesCom {
    
    private String request = "http://pensolut.com:8084/api/picture/addpictures";
    private DataOutputStream outStream;
    
    /*
     * Takes a list of pictures as parameter, stores them in a JSON format and
     * sends it to server for storage.
     * Returns: HTTP response
     */
    public int storePictures(ArrayList<Picture> pictureList) throws IOException{
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/v1+json");
        connection.setDoOutput(true);
        
        connection.connect();
 
        String body = "[";
        
        outStream = new DataOutputStream(connection.getOutputStream());
        for(int i=0; i<pictureList.size();i++){
            if(i < pictureList.size()-1){
                body = body+"{\n \"thumburl\": "+"\""+pictureList.get(i).thumbUrl+"\",\n";
                body = body+" \"url\": "+"\""+pictureList.get(i).largeUrl+"\"\n},";
            }else{
                body = body+"{\n \"thumburl\": "+"\""+pictureList.get(i).thumbUrl+"\",\n";
                body = body+" \"url\": "+"\""+pictureList.get(i).largeUrl+"\"\n}";
            }
        }

        body = body+"]";

        outStream.writeBytes(body);
        outStream.flush();
        outStream.close();
        
        return connection.getResponseCode();
    }
}
