package me.shib.java.lib.jiraclient.greenhopper;

import me.shib.java.lib.jiraclient.Field;
import me.shib.java.lib.jiraclient.RestClient;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Represents a GreenHopper sprint issue.
 */
public class SprintIssue extends GreenHopperIssue {

    private String epic = null;
    private EstimateStatistic estimateStatistic = null;

    /**
     * Creates a sprint issue from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected SprintIssue(RestClient restclient, JSONObject json) {
        super(restclient, json);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        epic = Field.getString(map.get("epic"));
        estimateStatistic = GreenHopperField.getEstimateStatistic(map.get("estimateStatistic"));
    }

    public String getEpic() {
        return epic;
    }

    public EstimateStatistic getEstimateStatistic() {
        return estimateStatistic;
    }
}

