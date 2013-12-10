package slideShow;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author mhovdan
 */
public class ImageTransitionTest {
    
    public ImageTransitionTest() {
    }

    @Test
    public void test1() 
    {
        ImageTransition trans = new ImageTransition();
        Image image = new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg");
        ImageView imageview = new ImageView(image);
        assertTrue(trans.getFullTransition(imageview) instanceof SequentialTransition);
    }
    
    @Test
    public void test2() 
    {
        assertTrue(ImageTransition.getTimeBetweenImages() < 60000);
    }
      
    @Test
    public void test3() 
    {
        assertTrue(ImageTransition.getTimeBetweenImages() > 0);
    }
    
    @Test
    public void test4() 
    {
        assertTrue(ImageTransition.getTransitionPause() instanceof PauseTransition);
    }
    
    @Test
    public void test5() 
    {
        assertTrue(ImageTransition.getTransitionStart(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg"))) instanceof FadeTransition);
    }
        
    @Test
    public void test6() 
    {
        assertTrue(ImageTransition.getTransitionStop(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg"))) instanceof FadeTransition);
    }
}
