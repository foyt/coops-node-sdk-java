package fi.foyt.coops.model;

public class FileRevision {

  public String getRevisionNumber() {
    return revisionNumber;
  }

  public void setRevisionNumber(String revisionNumber) {
    this.revisionNumber = revisionNumber;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getChecksum() {
    return checksum;
  }

  public void setChecksum(Long checksum) {
    this.checksum = checksum;
  }

  private String revisionNumber;
  
  private String userId;
  
  private Long checksum;
}
