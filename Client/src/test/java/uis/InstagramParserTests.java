/**
 * 
 */
package uis;

import static org.junit.Assert.*;

import java.io.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author glehen
 *
 */
public class InstagramParserTests {


	@Test
	public void Parse_GivenJsonWith15Pictures_ReturnsListWith15Pictures() throws FileNotFoundException, UnsupportedEncodingException {
		String file = getClass().getResource("/InstagramSampleData.txt").getFile();
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader ir = new InputStreamReader(fis, "UTF-8");
		

		InstagramParser parser = new InstagramParser();
		List<Picture> pictures = parser.parse(ir);
		assertThat(pictures.size(), is(15));
	}
	
	
	@Test
	public void Parse_GivenValidJson_FirstPictureHasUrl() throws FileNotFoundException, UnsupportedEncodingException {
		String file = getClass().getResource("/InstagramSampleData.txt").getFile();
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader ir = new InputStreamReader(fis, "UTF-8");
		

		InstagramParser parser = new InstagramParser();
		List<Picture> pictures = parser.parse(ir);
		assertThat(pictures.get(0).url, is(not(nullValue())));
	}

	
	@Test
	public void Parse_GivenValidJson_FirstPictureHasDescription() throws FileNotFoundException, UnsupportedEncodingException {
		String file = getClass().getResource("/InstagramSampleData.txt").getFile();
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader ir = new InputStreamReader(fis, "UTF-8");
		

		InstagramParser parser = new InstagramParser();
		List<Picture> pictures = parser.parse(ir);
		assertThat(pictures.get(0).description, is(not(nullValue())));
	}

}
