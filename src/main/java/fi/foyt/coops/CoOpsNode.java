package fi.foyt.coops;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import fi.foyt.coops.model.AccessToken;
import fi.foyt.coops.model.CompactUser;
import fi.foyt.coops.model.File;
import fi.foyt.coops.model.FileJoin;
import fi.foyt.coops.model.FileRevision;
import fi.foyt.coops.model.FileUser;
import fi.foyt.coops.model.FileUserRole;
import fi.foyt.coops.model.NewUser;
import fi.foyt.coops.model.Patch;
import fi.foyt.coops.model.User;

public class CoOpsNode extends CoOps {

  public CoOpsNode(String protocol, String host, int port, String basePath, String userId, String fileId) {
    super(protocol, host, port, basePath + "/1/users/" + userId + "/files/" + fileId);
    this.fileId = fileId;
    this.userId = userId;
  }
  
  public CoOpsNode(String protocol, String host, int port, String userId, String fileId) {
    this(protocol, host, port, "", userId, fileId);
  }

  /**
   * Joins collaboration session
   * 
   * @param accessToken bearer token 
   * @param algorithms algorithms supported by client in preference order
   * @return file join result
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public FileJoin joinFile(String accessToken, String[] algorithms) throws UsageException, ServerException, IOException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    return joinFile(algorithms, new BearerAuth(accessToken));
  }

  /**
   * Returns a file
   * 
   * @param accessToken bearer token 
   * @return a file
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public File getFile(String accessToken) throws UsageException, ServerException, IOException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }
    
    return getFile(new BearerAuth(accessToken));
  }
  
  /**
   * Returns specific version of a file
   * 
   * @param accessToken bearer token 
   * @param revisionNumber revision number that will be returned
   * @return file as it was in specified version
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public File getFileRevision(String accessToken, Long revisionNumber) throws UsageException, ServerException, IOException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }
    
    return getFileRevision(revisionNumber, new BearerAuth(accessToken));
  }
  
  /**
   * Saves a file
   * 
   * @param accessToken bearer token 
   * @param file a file
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public void saveFile(String accessToken, File file) throws UsageException, ServerException, IOException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }
    
    saveFile(file, new BearerAuth(accessToken));
  }
  
  /**
   * Patches a file
   * 
   * @param accessToken bearer token 
   * @param patch patch
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public void patchFile(String accessToken, Patch patch) throws UsageException, ServerException, IOException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }
    
    patchFile(patch, new BearerAuth(accessToken));
  }
  
  /**
   * List all users
   * 
   * @param accessToken Access Token
   * @return List of all users
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public List<CompactUser> getUsers(String accessToken) throws ServerException, IOException, UsageException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    return Arrays.asList(doGet(CompactUser[].class, "/1/users", new BearerAuth(accessToken)));
  }
  
  /**
   * Creates new user and returns access token which can be used for accessing user data in future
   * 
   * @param clientId client id
   * @param clientSecret client secret
   * @param user user to be created
   * @return created user
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public NewUser createUser(String clientId, String clientSecret, User user) throws UsageException, ServerException, IOException {
    if (user == null) {
      throw new UsageException("user can not be null");
    }
    
    if (StringUtils.isNotBlank(user.getId())) {
      throw new UsageException("User id can not be specified when creating a user");
    }
    
    return doPost(user, NewUser.class, "/1/users", CONTENT_TYPE_JSON, new ClientPasswordAuth(clientId, clientSecret));
  }
  
  /**
   * Returns a user
   * 
   * @param accessToken AccessToken
   * @param userId user's id to be returned
   * @return user
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public User getUser(String accessToken) throws UsageException, ServerException, IOException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    if (StringUtils.isBlank(userId)) {
      throw new UsageException("userId is required");
    }
    
    return doGet(User.class, "/1/users/" + userId, new BearerAuth(accessToken));
  }

  /**
   * Creates new file
   * 
   * @param accessToken AccessToken
   * @param userId owner's id
   * @param file file to be created
   * @return newly created file
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public File createFile(String accessToken, File file) throws UsageException, ServerException, IOException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    if (StringUtils.isBlank(userId)) {
      throw new UsageException("userId is required");
    }
    
    if (file == null) {
      throw new UsageException("file can not be null");
    }
    
    if (StringUtils.isNotBlank(file.getId())) {
      throw new UsageException("Id can not be specified when creating a file");
    }

    if (file.getRevisionNumber() != 0) {
      throw new UsageException("RevisionNumber must be 0 when creating a file");
    }
    
    if (file.getRole() != FileUserRole.OWNER) {
      throw new UsageException("Role must be OWNER when creating a file");
    }
    
    if (StringUtils.isBlank(file.getName())) {
      throw new UsageException("Name must be specified when creating a file");
    }

    if (StringUtils.isBlank(file.getContentType())) {
      throw new UsageException("ContentType must be specified when creating a file");
    }
    
    return doPost(file, File.class, "/1/users/" + userId + "/files", CONTENT_TYPE_JSON, new BearerAuth(accessToken));
  }

  /**
   * Lists user files. 
   * 
   * @param accessToken AccessToken
   * @param userId id of user who's files will be listed 
   * @return List of user's files
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public List<File> getFiles(String accessToken) throws ServerException, IOException, UsageException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    if (StringUtils.isBlank(userId)) {
      throw new UsageException("userId is required");
    }
    
    return Arrays.asList(doGet(File[].class, "/1/users/" + userId + "/files", new BearerAuth(accessToken)));
  }
  
  /**
   * Lists file users. 
   * 
   * @param accessToken AccessToken
   * @param userId id of user who's file users we are listing
   * @param fileId id of file which users we are listing
   * @return list of file users
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public List<FileUser> getFileUsers(String accessToken) throws ServerException, IOException, UsageException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    if (StringUtils.isBlank(userId)) {
      throw new UsageException("userId is required");
    }
    
    if (StringUtils.isBlank(fileId)) {
      throw new UsageException("fileId is required");
    }    
    
    return Arrays.asList(doGet(FileUser[].class, "/1/users/" + userId + "/files/" + fileId + "/users", new BearerAuth(accessToken)));
  }
  
  public void updateFileUsers(String accessToken, List<FileUser> fileUsers) throws ServerException, IOException, UsageException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    if (StringUtils.isBlank(userId)) {
      throw new UsageException("userId is required");
    }
    
    if (StringUtils.isBlank(fileId)) {
      throw new UsageException("fileId is required");
    }    
    
    if ((fileUsers == null)||(fileUsers.size() == 0)) {
      throw new UsageException("fileUsers need to be defined");
    }
    
    doPost(fileUsers, null, "/1/users/" + userId + "/files/" + fileId + "/users", CONTENT_TYPE_JSON, new BearerAuth(accessToken));
  }
  
  /**
   * Lists file revisions. 
   * 
   * @param accessToken AccessToken
   * @param userId id of user who's file revisions we are listing
   * @param fileId id of file which revisions we are listing
   * @return list of file revisions
   * @throws ServerException ServerException is thrown when server returns does not return a successful result
   * @throws IOException IOException is thrown when error occurs in server communication
   * @throws UsageException UsageException is thrown when method is called incorrectly
   */
  public List<FileRevision> getFileRevisions(String accessToken) throws ServerException, IOException, UsageException {
    if (StringUtils.isBlank(accessToken)) {
      throw new UsageException("AccessToken is required");
    }

    if (StringUtils.isBlank(userId)) {
      throw new UsageException("userId is required");
    }
    
    if (StringUtils.isBlank(fileId)) {
      throw new UsageException("fileId is required");
    }    
    
    return Arrays.asList(doGet(FileRevision[].class, "/1/users/" + userId + "/files/" + fileId + "/revisions", new BearerAuth(accessToken)));
  }

  public AccessToken refreshAccessToken(String clientId, String clientSecret, String refreshToken) throws ServerException, IOException {
    String body = new StringBuilder()
      .append("refresh_token=")
      .append(URLEncoder.encode(refreshToken, "UTF-8"))
      .append("&grant_type=refresh_token")
      .toString();
    
    return objectFromJson(AccessToken.class, getIoHandler().doPostRequest(getURI("/oauth2/token"), body, "application/x-www-form-urlencoded", new ClientPasswordAuth(clientId, clientSecret)));
  }
  
  private String fileId;
  private String userId;
  
  /** 
   * Client Password strategy. Used for client authentication.
   */
  private class ClientPasswordAuth implements Auth {

    public ClientPasswordAuth(String clientId, String clientSecret) {
      byte[] bytes = new StringBuilder().append(clientId).append(':').append(clientSecret).toString().getBytes();
      this.encoded = Base64.encodeBase64String(bytes);
    }
    
    @Override
    public Map<String, String> getHeaders() {
      Map<String, String> result = new HashMap<String, String>();
      result.put("Authorization", "Basic " + this.encoded);
      return result;
    }

    private String encoded;
  }

  /**
   * Bearer auth. Used for authenticating user with bearer tokens.
   */
  private class BearerAuth implements Auth {

    public BearerAuth(String accessToken) {
      this.accessToken = accessToken;
    }
    
    @Override
    public Map<String, String> getHeaders() {
      Map<String, String> result = new HashMap<String, String>();
      result.put("Authorization", "Bearer " + this.accessToken);
      return result;
    }

    private String accessToken;
  }
}
