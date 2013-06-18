package fi.foyt.coops.model;

public class NewUser extends User {

  public AccessToken getAccessToken() {
    return access_token;
  }

  public void setAccessToken(AccessToken accessToken) {
    this.access_token = accessToken;
  }

  private AccessToken access_token;
}
