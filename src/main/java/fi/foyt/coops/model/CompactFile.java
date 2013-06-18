package fi.foyt.coops.model;

public class CompactFile {

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FileUserRole getRole() {
    return role;
  }

  public void setRole(FileUserRole role) {
    this.role = role;
  }

  private String id;
  private String name;
  private FileUserRole role;
}
