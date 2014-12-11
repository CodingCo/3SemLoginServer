package facadeTests;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import model.UserInfo;
import webinterfaces.UserFacadeInterface;

/**
 *
 * @author christophermortensen
 */
public class UserFacadeMock implements UserFacadeInterface{

    private List<UserInfo> users;
    private Gson transformer;
    
    public UserFacadeMock(Gson transformer){
        this.users = new ArrayList<>();
        this.transformer = transformer;
    }
    
    @Override
    public String getUsersAsJSON() {
        return transformer.toJson(users);
    }

    @Override
    public String getOneUserAsJSON(String username) { // Before: long user_id 
        for(UserInfo user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return transformer.toJson(user);
            }
        }
        return null;
    }

    @Override
    public UserInfo createUserFromJSON(String json) {
        UserInfo userToCreate = transformer.fromJson(json, UserInfo.class);
        userToCreate.setUser_id(99999l + (users.size() + 1));
        users.add(userToCreate);
        return userToCreate;
    }

    @Override
    public UserInfo editUser(String json, String username) {
        for(UserInfo user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                UserInfo editedUser = transformer.fromJson(json, UserInfo.class);
                user.setUsername(editedUser.getUsername());
                user.setEmail(editedUser.getEmail());
                user.setPassword(editedUser.getPassword());
                return user;
            }
        }
        return null;
    }

    @Override
    public UserInfo deleteUser(String username) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equalsIgnoreCase(username)){
                UserInfo userToDelete = users.get(i);
                users.remove(i);
                return userToDelete;
            }
        }
        return null;
    }

    @Override
    public boolean validateUser(String json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
