/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;

/**
 *
 * @author T
 */
public class AuthenticationParser {

    /**
     * Parses the InputStreamReader to find the access_token/bearer_token, returns the token.
     * @param reader (InputStreamReader)
     * @return bearerToken (String)
     */
    public String parse(InputStreamReader reader) {
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(reader).getAsJsonObject();
        String tokenType = obj.get("token_type").getAsString();
        String token = obj.get("access_token").getAsString();
        return ((tokenType.equals("bearer")) && (token != null)) ? token : "";
    }
}
