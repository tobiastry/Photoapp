package slideShow;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhovdan
 */
public class ListOfImagesTest{
    
    public ListOfImagesTest() {
    }
    
    @Test
    public void test1(){
        assertTrue(ListOfImages.getImageList() instanceof String[]);
    }
    
    @Test
    public void test2(){
        assertTrue(ListOfImages.getImageList()[0] instanceof String);
    }
    
    @Test
    public void test3(){
        String imagePath1 = ListOfImages.getImageList()[0];
        assertEquals(imagePath1, ListOfImages.getImageList()[0]);
    }
    
    @Test
    public void test4(){
        assertTrue(ListOfImages.getImageViewList() instanceof ImageView[]);
    }
    
    @Test
    public void test5(){
        assertTrue(ListOfImages.getImageViewList()[0] instanceof ImageView);
    }
        @Test
    public void test6(){
        assertTrue(ListOfImages.getImageViewList()[0].getImage() instanceof Image);
    }
}