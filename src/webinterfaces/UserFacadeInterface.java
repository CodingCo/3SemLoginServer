package webinterfaces;

import model.UserInfo;

/**
 *
 * @author christophermortensen
 */
public interface UserFacadeInterface {
    
    public String getUsersAsJSON();
    
    public String getOneUserAsJSON(String userAsJson);
    
    public boolean validateUser(String json);
    
    public UserInfo createUserFromJSON(String json);
    
    public UserInfo editUser(String json, long user_id);
    
    public UserInfo deleteUser(long user_id);
    
}
