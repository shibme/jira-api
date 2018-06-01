package me.shib.java.lib.jiraclient.agile;

import me.shib.java.lib.jiraclient.Field;
import me.shib.java.lib.jiraclient.JiraException;
import me.shib.java.lib.jiraclient.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile Status.
 *
 * @author pldupont
 */
public class Status extends AgileResource {

    private String description;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Status(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }

    /**
     * Deserialize the json to extract standard attributes and keep a reference of
     * other attributes.
     *
     * @param json The JSON object to read.
     */
    @Override
    void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);

        this.description = Field.getString(json.get("description"));
    }

    public String getDescription() {
        return description;
    }
}
