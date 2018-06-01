package me.shib.java.lib.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Represents an custom field option.
 */
public class CustomFieldOption extends Resource {

    private String value = null;

    /**
     * Creates a custom field option from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected CustomFieldOption(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Retrieves the given custom field option record.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the custom field option
     * @return a custom field option instance
     * @throws JiraException when the retrieval fails
     */
    public static CustomFieldOption get(RestClient restclient, String id)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "customFieldOption/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve custom field option " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new CustomFieldOption(restclient, (JSONObject) result);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        value = Field.getString(map.get("value"));
    }

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return value;
    }
}

