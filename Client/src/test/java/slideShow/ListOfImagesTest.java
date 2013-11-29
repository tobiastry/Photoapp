package slideShow;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhovdan
 */
public class ListOfImagesTest{
    Slideshow slide = new Slideshow();
    ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
    ListOfImages list = new ListOfImages(imageViewList);
    
    public ListOfImagesTest() {
    }
    
    
    /*
     * MÅ LAGES NYE TESTER TIL KLASSEN!
     */
   /* @Test
    public void test1(){
        assertTrue(list.getImageList() instanceof String[]);
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
    }*/
}