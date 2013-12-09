/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author T
 */
public class AuthenticationTwitterTests {

    @Test
    public void GetPictures_WhenCalled_ReturnsListOfPictures() throws IOException {
        AuthenticationTwitter auth = new AuthenticationTwitter();
        String bearertoken = auth.requestBearerToken();
        assertThat(bearertoken, is(not(nullValue())));
    }
}