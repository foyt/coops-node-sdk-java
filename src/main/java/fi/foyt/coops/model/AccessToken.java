package fi.foyt.coops.model;

public class AccessToken {

  public String getAccessToken() {
    return access_token;
  }

  public void setAccessToken(String accessToken) {
    this.access_token = accessToken;
  }

  public String getTokenType() {
    return token_type;
  }

  public void setTokenType(String tokenType) {
    this.token_type = tokenType;
  }

  public Long getExpiresIn() {
    return expires_in;
  }

  public void setExpiresIn(Long expiresIn) {
    this.expires_in = expiresIn;
  }

  public String getRefreshToken() {
    return refresh_token;
  }

  public void setRefreshToken(String refreshToken) {
    this.refresh_token = refreshToken;
  }

  private String access_token;
  private String token_type;
  private Long expires_in;
  private String refresh_token;
}
