package fi.foyt.coops.model;

public class FileUser {

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public FileUserRole getRole() {
    return role;
  }

  public void setRole(FileUserRole role) {
    this.role = role;
  }

  private String userId;
  private FileUserRole role;
}
