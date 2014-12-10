package webinterfaces;

import model.User;

/**
 *
 * @author christophermortensen
 */
public interface UserFacadeInterface {
    
    public String getUsersAsJSON();
    
    public String getOneUserAsJSON(long user_id);
    
    public User createUserFromJSON(String json);
    
    public User editUser(String json, long user_id);
    
    public User deleteUser(long user_id);
    
}
