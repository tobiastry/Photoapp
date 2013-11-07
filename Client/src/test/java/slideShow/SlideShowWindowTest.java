package slideShow;

import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhovdan
 */
public class SlideShowWindowTest {
    
    public SlideShowWindowTest() {
    }

    @Test
    public void test1() 
    {
        assertTrue(SlideShowWindow.getSlideShowWindow() instanceof Stage);
    }
    
    @Test
    public void test2() 
    {
        Stage slideshowvindu = SlideShowWindow.getSlideShowWindow();
        assertTrue(slideshowvindu.isFullScreen());
    }
    
    @Test
    public void test3() 
    {
        Stage slideshowvindu = SlideShowWindow.getSlideShowWindow();
        assertTrue(slideshowvindu.isResizable());
    } 
}