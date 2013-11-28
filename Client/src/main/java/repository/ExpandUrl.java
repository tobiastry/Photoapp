/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author John McEpic
 */
public class ExpandUrl {

    public String expand(String pictureUrl) throws MalformedURLException, IOException {
        final URL url = new URL(pictureUrl);
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setInstanceFollowRedirects(false);
        final String location = urlConnection.getHeaderField("location");
        return location.toString();
    }
}
