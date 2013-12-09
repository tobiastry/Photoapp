/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author T
 */
public class ExpandUrlTest {
    
    @Test
    public void GetPictures_WhenCalled_ReturnsListOfPictures() throws IOException {
        ExpandUrl expand = new ExpandUrl();
        String shortUrl = "http://twitpic.com/show/large/1e10q"; //example link from the Twitpic dev API
        String longUrl = expand.expand(shortUrl);
        assertEquals(longUrl, "http://d3j5vwomefv46c.cloudfront.net/photos/large/2334122.jpg?1234102459");
    }
}