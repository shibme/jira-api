package me.shib.java.lib.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Represents an issue component.
 */
public class Component extends Resource {

    private String name = null;
    private String description = null;
    private boolean isAssigneeTypeValid = false;

    /**
     * Creates a component from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected Component(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Retrieves the given component record.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the component
     * @return a component instance
     * @throws JiraException when the retrieval fails
     */
    public static Component get(RestClient restclient, String id)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getRestUri(id));
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve component " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Component(restclient, (JSONObject) result);
    }

    private static String getRestUri(String id) {
        return getBaseUri() + "component/" + (id != null ? id : "");
    }

    /**
     * Creates a new JIRA component.
     *
     * @param restclient REST client instance
     * @param project    Key of the project to create the component in
     * @return a fluent create instance
     */
    public static FluentCreate create(RestClient restclient, String project) {
        FluentCreate fc = new FluentCreate(restclient, project);
        return fc;
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        name = Field.getString(map.get("name"));
        description = Field.getString(map.get("description"));
        isAssigneeTypeValid = Field.getBoolean(map.get("isAssigneeTypeValid"));
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAssigneeTypeValid() {
        return isAssigneeTypeValid;
    }

    /**
     * Deletes a component from a project.
     *
     * @throws JiraException failed to delete the component
     */
    public void delete() throws JiraException {
        try {
            restclient.delete(getRestUri(id));
        } catch (Exception ex) {
            throw new JiraException("Failed to delete component " + id, ex);
        }
    }

    /**
     * Used to chain fields to a create action.
     */
    public static final class FluentCreate {
        /**
         * The Jira rest client.
         */
        RestClient restclient = null;

        /**
         * The JSON request that will be built incrementally as fluent methods
         * are invoked.
         */
        JSONObject req = new JSONObject();

        /**
         * Creates a new fluent.
         *
         * @param restclient the REST client
         * @param project    the project key
         */
        private FluentCreate(RestClient restclient, String project) {
            this.restclient = restclient;
            req.put("project", project);
        }

        /**
         * Sets the name of the component.
         *
         * @param name the name
         * @return <code>this</code>
         */
        public FluentCreate name(String name) {
            req.put("name", name);
            return this;
        }

        /**
         * Sets the description of the component.
         *
         * @param description the description
         * @return <code>this</code>
         */
        public FluentCreate description(String description) {
            req.put("description", description);
            return this;
        }

        /**
         * Sets the lead user name.
         *
         * @param leadUserName the lead user name
         * @return <code>this</code>
         */
        public FluentCreate leadUserName(String leadUserName) {
            req.put("leadUserName", leadUserName);
            return this;
        }

        /**
         * Sets the assignee type.
         *
         * @param assigneeType the assignee type
         * @return <code>this</code>
         */
        public FluentCreate assigneeType(String assigneeType) {
            req.put("assigneeType", assigneeType);
            return this;
        }

        /**
         * Sets whether the assignee type is valid.
         *
         * @param assigneeTypeValid is the assignee type valid?
         * @return <code>this</code>
         */
        public FluentCreate assigneeTypeValue(boolean assigneeTypeValid) {
            req.put("isAssigneeTypeValid", assigneeTypeValid);
            return this;
        }

        /**
         * Executes the create action.
         *
         * @return the created component
         * @throws JiraException when the create fails
         */
        public Component execute() throws JiraException {
            JSON result = null;

            try {
                result = restclient.post(getRestUri(null), req);
            } catch (Exception ex) {
                throw new JiraException("Failed to create issue", ex);
            }

            if (!(result instanceof JSONObject) || !((JSONObject) result).containsKey("id")
                    || !(((JSONObject) result).get("id") instanceof String)) {
                throw new JiraException("Unexpected result on create component");
            }

            return new Component(restclient, (JSONObject) result);
        }
    }
}

