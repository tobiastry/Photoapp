
package slideShow;

import javafx.concurrent.Task;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhovdan
 */
public class CheckNewDelayTest {
    CheckNewDelay testCheck = new CheckNewDelay(); 
    
    public CheckNewDelayTest() {
     
    }
    
    @Test
    public void test1(){
        assertTrue(testCheck.checkNewDelay() instanceof Task);
    }
    
    @Test
    public void test2(){
        assertTrue(CheckNewDelay.getDelay() > 0);
    }
    
    @Test
    public void test3(){
        assertTrue(CheckNewDelay.getDelay() < 600);
    }
}