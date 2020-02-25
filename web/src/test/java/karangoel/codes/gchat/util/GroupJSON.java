package karangoel.codes.gchat.util;

import org.json.JSONException;
import org.json.JSONObject;

final public class GroupJSON {
    // == jsons ==
    public static JSONObject createGroup(String name) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("name", name);
        return data;
    }
}
