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
 * @author John McEpic
 */
public class AuthenticationParser {
    public String parse(InputStreamReader reader) {
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(reader).getAsJsonObject();
        String tokenType = obj.get("token_type").getAsString();
        String token = obj.get("access_token").getAsString();
        return ((tokenType.equals("bearer")) && (token != null)) ? token : "";
    }
}
