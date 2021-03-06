package me.shib.java.lib.jiraclient.greenhopper;

import me.shib.java.lib.jiraclient.Field;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * GreenHopper estimate sum for rapid views.
 */
public class EstimateSum {

    private Double value = null;
    private String text = null;

    /**
     * Creates an estimate sum from a JSON payload.
     *
     * @param json JSON payload
     */
    protected EstimateSum(JSONObject json) {
        Map map = json;

        value = Field.getDouble(map.get("value"));
        text = Field.getString(map.get("text"));
    }

    public Double getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

