package karangoel.codes.gchat.util;

import org.json.JSONException;
import org.json.JSONObject;

final public class UserJSON {
    // == jsons ==
    public static JSONObject createUserSignUp(String email, String firstName, String lastName, String password)
            throws JSONException {
        JSONObject data = new JSONObject();
        data.put("email",  email);
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("password", password);;
        return data;
    }

    public static JSONObject createUserSignIn(String email, String password)
            throws JSONException{
        JSONObject data = new JSONObject();
        data.put("email",email);
        data.put("password",password);
        return data;
    }

}
