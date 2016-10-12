package json;

import com.google.gson.JsonArray;

/**
 * Created by misterbykl
 * 12.10.2016 - 22:00
 */
public class JsonManager {

    private JsonArray jsonElements = new JsonArray();

    /**
     * @param argJsonElements Created by misterbykl
     *                        12.10.2016 - 22:03
     */
    public void setJsonElements(JsonArray argJsonElements) {
        this.jsonElements.addAll(argJsonElements);
    }

    /**
     * @return Created by misterbykl
     * 12.10.2016 - 22:03
     */
    public JsonArray getJsonElements() {
        return this.jsonElements;
    }
}
