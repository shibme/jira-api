package me.shib.java.lib.jiraclient.greenhopper;

import me.shib.java.lib.jiraclient.Field;
import me.shib.java.lib.jiraclient.JiraException;
import me.shib.java.lib.jiraclient.Project;
import me.shib.java.lib.jiraclient.RestClient;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Represents a GreenHopper JIRA project.
 */
public class RapidViewProject extends GreenHopperResource {

    private String key = null;
    private String name = null;

    /**
     * Creates a project from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected RapidViewProject(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getInteger(map.get("id"));
        key = Field.getString(map.get("key"));
        name = Field.getString(map.get("name"));
    }

    /**
     * Retrieves the full JIRA project.
     *
     * @return a Project
     * @throws JiraException when the retrieval fails
     */
    public Project getJiraProject() throws JiraException {
        return Project.get(restclient, key);
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

