package me.shib.java.lib.jiraclient.greenhopper;

import me.shib.java.lib.jiraclient.JiraClient;
import me.shib.java.lib.jiraclient.JiraException;
import me.shib.java.lib.jiraclient.RestClient;

import java.util.List;

/**
 * A GreenHopper extension to the JIRA client.
 */
public class GreenHopperClient {

    private RestClient restclient = null;

    /**
     * Creates a GreenHopper client.
     *
     * @param jira JIRA client
     */
    public GreenHopperClient(JiraClient jira) {
        restclient = jira.getRestClient();
    }

    /**
     * Retreives the rapid view with the given ID.
     *
     * @param id Rapid View ID
     * @return a RapidView instance
     * @throws JiraException when something goes wrong
     */
    public RapidView getRapidView(int id) throws JiraException {
        return RapidView.get(restclient, id);
    }

    /**
     * Retreives all rapid views visible to the session user.
     *
     * @return a list of rapid views
     * @throws JiraException when something goes wrong
     */
    public List<RapidView> getRapidViews() throws JiraException {
        return RapidView.getAll(restclient);
    }
}

