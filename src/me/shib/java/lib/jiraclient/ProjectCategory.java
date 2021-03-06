package me.shib.java.lib.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Represents a project category.
 */
public class ProjectCategory extends Resource {

    private String name = null;
    private String description = null;

    /**
     * Creates a category from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected ProjectCategory(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Retrieves the given status record.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the status
     * @return a status instance
     * @throws JiraException when the retrieval fails
     */
    public static ProjectCategory get(RestClient restclient, String id)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "projectCategory/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve status " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new ProjectCategory(restclient, (JSONObject) result);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        description = Field.getString(map.get("description"));
        name = Field.getString(map.get("name"));
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}

