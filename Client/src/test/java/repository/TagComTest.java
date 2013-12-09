package repository;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagComTest {
    private ArrayList<String> tags;
    private TagCom instance;

    public TagComTest() {
    }

    @Before
    public void setUp() {
        tags = new ArrayList();
        tags.add("Taggg1");
        instance = new TagCom();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of storeTag method, of class TagCom.
     */
    @Test
    public void testStoreTag() throws Exception {
        System.out.println("storeTag");
        int expResult = 200;
        int result = instance.storeTag("Taggg1");
        assertEquals(expResult, result);
        instance.removeTag(tags);
    }

    /**
     * Test of getTags method, of class TagCom.
     */
    @Test
    public void testGetTags() throws Exception {
        System.out.println("getTags");
        instance.storeTag("Taggg1");
        ArrayList<String> result = instance.getTags();
        assertEquals(tags.get(0), result.get(result.size() -1));
        instance.removeTag(tags);
    }

    /**
     * Test of removeTag method, of class TagCom.
     */
    @Test
    public void testRemoveTag() throws Exception {
        System.out.println("removeTag");
        instance.storeTag("Taggg1");
        instance.removeTag(tags);
        ArrayList<String> result = instance.getTags();
        boolean b = false;
        for(String s: result){
            if(s.equals("Taggg1")){
                b = true;
            }
        }
        assertFalse(b);
    }
}
