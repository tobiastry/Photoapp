package repository;

import org.junit.Test;
import static org.junit.Assert.*;

public class DelayComTest {

    public DelayComTest() {
    }

    /**
     * Test of getDelay method, of class DelayCom.
     */
    @Test
    public void testGetDelay() throws Exception {
        System.out.println("getDelay");
        DelayCom instance = new DelayCom();
        boolean expResult;
        int result = instance.getDelay();
        expResult = result > 0;
        assertTrue("getDelay did not return a positive int", expResult);
    }

    /**
     * Test of setDelay method, of class DelayCom.
     */
    @Test
    public void testSetDelay() throws Exception {
        System.out.println("setDelay");
        int delay = 10;
        DelayCom instance = new DelayCom();
        int expResult = 10;
        int result = instance.setDelay(delay);
        assertEquals(expResult, result);
    }
}