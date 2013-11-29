/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Emil
 */
public class DeletePicturesComTest {

    /**
     * Test of deletePictures method, of class DeletePicturesCom.
     */
    @Test
    public void testDeletePictures() throws Exception {
        System.out.println("deletePictures");
        ArrayList<String> imageList = new ArrayList();
        imageList.add("http://d3j5vwomefv46c.cloudfront.net/photos/large/415070043.jpg");
        DeletePicturesCom instance = new DeletePicturesCom();
        int result = instance.deletePictures(imageList);
        assertEquals(result, 200);
    }
}