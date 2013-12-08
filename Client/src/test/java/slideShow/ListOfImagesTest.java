package slideShow;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.concurrent.Task;
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
    
    
    @Test
    public void test1(){
        assertTrue(list.getImageViewList() instanceof Task);
    }
    
    @Test
    public void test2(){
        assertTrue(list instanceof ListOfImages);
    }
    
    @Test
    public void test3(){
        assertTrue(slide instanceof Application);
    }
    
    @Test
    public void test4(){
        assertEquals(slide.getSlideshowObject(), slide);
    }
    

}