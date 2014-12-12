package webinterfaces;

import model.UserInfo;

/**
 *
 * @author christophermortensen
 */
public interface UserFacadeInterface {
    
    public String getUsersAsJSON();
    
    public String getOneUserAsJSON(String username);
    
    public boolean validateUser(String json);
    
    public UserInfo createUserFromJSON(String json);
    
    public UserInfo editUser(String json, String username);
    
    public UserInfo deleteUser(String username);
    
}
