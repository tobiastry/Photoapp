/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sletting;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Emil
 */
public class TesttesterTest {
    
    public TesttesterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of metode1 method, of class Testtester.
     */
    @Test
    public void testMetode1() {
        System.out.println("metode1");
        int tall = 0;
        Testtester instance = new Testtester();
        int expResult = 0;
        int result = instance.metode1(tall);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of metode2 method, of class Testtester.
     */
    @Test
    public void testMetode2() {
        System.out.println("metode2");
        String ad = "";
        Testtester instance = new Testtester();
        String expResult = "";
        String result = instance.metode2(ad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}