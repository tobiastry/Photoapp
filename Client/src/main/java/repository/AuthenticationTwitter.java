package repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author John McEpic
 */
public class AuthenticationTwitter {

    static String encodedCredentials = encodeKeys("HjMvuT341pNEGvodae75Ig", "LgU2yoVAkYuRKGLbez8k5PwwuBGQjQ75JfupN1zL38");

    private static String encodeKeys(String consumerKey, String consumerSecret) {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");

            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
            return new String(encodedBytes);
        } catch (UnsupportedEncodingException e) {
            return new String();
        }
    }

    /**
     * Requests a bearer_token from Twitters api, returns the token as a string.
     *
     * @return bearer_token (String)
     * @throws IOException
     */
    public String requestBearerToken() throws IOException {
        HttpsURLConnection connection = null;
        //String encodedCredentials = encodeKeys("HjMvuT341pNEGvodae75Ig","LgU2yoVAkYuRKGLbez8k5PwwuBGQjQ75JfupN1zL38");

        try {
            URL url = new URL("https://api.twitter.com/oauth2/token");
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "Photoappdat210");
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("Content-Length", "29");
            connection.setUseCaches(false);
            writeRequest(connection, "grant_type=client_credentials");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            AuthenticationParser parser = new AuthenticationParser();
            String bearertoken = parser.parse(reader);

            if (bearertoken != null) {
                return bearertoken;
            }
            return new String();
        } catch (MalformedURLException e) {

            throw new IOException("Invalid endpoint URL specified.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static boolean writeRequest(HttpsURLConnection connection, String textBody) {
        try {
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            wr.write(textBody);
            wr.flush();
            wr.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
