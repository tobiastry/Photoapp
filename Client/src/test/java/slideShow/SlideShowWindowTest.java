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
        assertTrue(new SlideShowWindow() instanceof Stage);
    }
    
    @Test
    public void test2() 
    {
        Stage slideshowvindu = new SlideShowWindow();
        assertTrue(slideshowvindu.isFullScreen());
    }
    
    @Test
    public void test3() 
    {
        Stage slideshowvindu = new SlideShowWindow();
        assertTrue(slideshowvindu.isResizable());
    } 
}