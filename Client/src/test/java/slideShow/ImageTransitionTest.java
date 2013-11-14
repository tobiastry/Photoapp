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
        Image image = new Image("test");
        ImageView imageview = new ImageView(image);
        assertTrue(ImageTransition.getFullOvergang(imageview) instanceof SequentialTransition);
    }
    
    @Test
    public void test2() 
    {
        Image image = new Image("test");
        ImageView imageview = new ImageView(image);
        assertTrue(ImageTransition.getHalvOvergang(imageview) instanceof SequentialTransition);
    }
    
    @Test
    public void test3() 
    {
        assertEquals(ImageTransition.getTidMellomBilder(), 2000 );
    }
    
    @Test
    public void test4() 
    {
        assertTrue(ImageTransition.getOvergangPause() instanceof PauseTransition);
    }
    
    @Test
    public void test5() 
    {
        assertTrue(ImageTransition.getOvergangStart(new ImageView(new Image("test"))) instanceof FadeTransition);
    }
        
    @Test
    public void test6() 
    {
        assertTrue(ImageTransition.getOvergangStopp(new ImageView(new Image("test"))) instanceof FadeTransition);
    }
}