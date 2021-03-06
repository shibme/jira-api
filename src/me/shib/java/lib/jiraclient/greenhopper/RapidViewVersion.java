package me.shib.java.lib.jiraclient.greenhopper;

import me.shib.java.lib.jiraclient.Field;
import me.shib.java.lib.jiraclient.JiraException;
import me.shib.java.lib.jiraclient.RestClient;
import me.shib.java.lib.jiraclient.Version;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Represents a GreenHopper JIRA project version.
 */
public class RapidViewVersion extends GreenHopperResource {

    private String name = null;
    private int sequence = 0;
    private boolean released = false;

    /**
     * Creates a version from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected RapidViewVersion(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getInteger(map.get("id"));
        name = Field.getString(map.get("name"));
        sequence = Field.getInteger(map.get("sequence"));
        released = Field.getBoolean(map.get("released"));
    }

    /**
     * Retrieves the full JIRA version.
     *
     * @return a Version
     * @throws JiraException when the retrieval fails
     */
    public Version getJiraVersion() throws JiraException {
        return Version.get(restclient, Integer.toString(id));
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getSequence() {
        return sequence;
    }

    public boolean isReleased() {
        return released;
    }
}

