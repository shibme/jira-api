package me.shib.java.lib.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a JIRA project.
 */
public class Project extends Resource {

    private Map<String, String> avatarUrls = null;
    private String key = null;
    private String name = null;
    private String description = null;
    private User lead = null;
    private String assigneeType = null;
    private List<Component> components = null;
    private List<IssueType> issueTypes = null;
    private List<Version> versions = null;
    private Map<String, String> roles = null;
    private ProjectCategory category = null;
    private String email = null;

    /**
     * Creates a project from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected Project(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Retrieves the given project record.
     *
     * @param restclient REST client instance
     * @param key        Project key
     * @return a project instance
     * @throws JiraException when the retrieval fails
     */
    public static Project get(RestClient restclient, String key)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "project/" + key);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve project " + key, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Project(restclient, (JSONObject) result);
    }

    /**
     * Retrieves all project records visible to the session user.
     *
     * @param restclient REST client instance
     * @return a list of projects
     * @throws JiraException when the retrieval fails
     */
    public static List<Project> getAll(RestClient restclient) throws JiraException {
        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "project");
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve projects", ex);
        }

        if (!(result instanceof JSONArray))
            throw new JiraException("JSON payload is malformed");

        return Field.getResourceArray(Project.class, result, restclient);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        avatarUrls = Field.getMap(String.class, String.class, map.get("avatarUrls"));
        key = Field.getString(map.get("key"));
        name = Field.getString(map.get("name"));
        description = Field.getString(map.get("description"));
        lead = Field.getResource(User.class, map.get("lead"), restclient);
        assigneeType = Field.getString(map.get("assigneeType"));
        components = Field.getResourceArray(Component.class, map.get("components"), restclient);
        issueTypes = Field.getResourceArray(
                IssueType.class,
                map.containsKey("issueTypes") ? map.get("issueTypes") : map.get("issuetypes"),
                restclient);
        versions = Field.getResourceArray(Version.class, map.get("versions"), restclient);
        roles = Field.getMap(String.class, String.class, map.get("roles"));
        category = Field.getResource(ProjectCategory.class, map.get("projectCategory"), restclient);
        email = Field.getString(map.get("email"));
    }

    public List<User> getAssignableUsers() throws JiraException {
        JSON result = null;

        try {
            Map<String, String> queryParams = new HashMap<String, String>();
            queryParams.put("project", this.key);
            URI searchUri = restclient.buildURI(getBaseUri() + "user/assignable/search", queryParams);
            result = restclient.get(searchUri);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve assignable users", ex);
        }

        if (!(result instanceof JSONArray))
            throw new JiraException("JSON payload is malformed");

        return Field.getResourceArray(User.class, result, restclient);
    }

    @Override
    public String toString() {
        return getName();
    }

    public Map<String, String> getAvatarUrls() {
        return avatarUrls;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getLead() {
        return lead;
    }

    public String getAssigneeType() {
        return assigneeType;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<IssueType> getIssueTypes() {
        return issueTypes;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public ProjectCategory getCategory() {
        return category;
    }

    public String getEmail() {
        return email;
    }
}

