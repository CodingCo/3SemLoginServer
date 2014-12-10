package facadeTests;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import model.User;
import webinterfaces.UserFacadeInterface;

/**
 *
 * @author christophermortensen
 */
public class UserFacadeMock implements UserFacadeInterface{

    private List<User> users;
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
    public String getOneUserAsJSON(long user_id) {
        for(User user : users){
            if(user.getUser_id() == user_id){
                return transformer.toJson(user);
            }
        }
        return null;
    }

    @Override
    public User createUserFromJSON(String json) {
        User userToCreate = transformer.fromJson(json, User.class);
        userToCreate.setUser_id(99999l + (users.size() + 1));
        users.add(userToCreate);
        return userToCreate;
    }

    @Override
    public User editUser(String json, long user_id) {
        for(User user : users){
            if(user.getUser_id() == user_id){
                User editedUser = transformer.fromJson(json, User.class);
                user.setUsername(editedUser.getUsername());
                user.setEmail(editedUser.getEmail());
                user.setPassword(editedUser.getPassword());
                return user;
            }
        }
        return null;
    }

    @Override
    public User deleteUser(long user_id) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUser_id() == user_id){
                User userToDelete = users.get(i);
                users.remove(i);
                return userToDelete;
            }
        }
        return null;
    }
}
