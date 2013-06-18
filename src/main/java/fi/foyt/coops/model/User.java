package fi.foyt.coops.model;

import java.util.Map;

public class User extends CompactUser {

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  private Map<String, String> properties;
}
