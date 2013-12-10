package imageGetters;

import com.google.gson.JsonArray;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author T
 */
public class InstagramGetterTests {

    @Test
    public void GetPictures_WhenCalled_ReturnsListOfPictures() throws IOException {
        InstagramGetter instaGetter = new InstagramGetter();
        String tagString = "nofilter";
        String urlString = instaGetter.toUrl(tagString);
        JsonArray jsonPictures = instaGetter.findPictures(urlString);
        assertNotSame(jsonPictures.size(), 0);
    }
}
