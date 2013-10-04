package uis;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class InstagramGetterTests {

	@Test
	public void GetPictures_WhenCalled_ReturnsListOfPictures() throws IOException {
		InstagramGetter getter = new InstagramGetter();
		List<Picture> pictures = getter.getPictures();
		assertThat(pictures.isEmpty(), is(false));
	}

}
