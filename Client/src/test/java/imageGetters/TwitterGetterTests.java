package imageGetters;

import com.google.gson.JsonArray;
import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

public class TwitterGetterTests {

    @Test
    public void GetPictures_WhenCalled_ReturnsListOfPictures() throws IOException {
        TwitterGetter twitterGetter = new TwitterGetter();
        String tagString = "nofilter";
        String urlString = twitterGetter.toUrl(tagString);
        JsonArray jsonPictures = twitterGetter.findPictures(urlString);
        assertNotSame(jsonPictures.size(), 0);
    }
}
