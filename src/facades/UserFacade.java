package facades;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import model.UserInfo;
import webServer.Factory;
import webServer.HashCreator;
import webinterfaces.UserFacadeInterface;

public class UserFacade implements UserFacadeInterface {

    private Gson gson;
    private EntityManager em;
    private Factory fac;

    public UserFacade(Gson gson) {
        this.gson = gson;
        this.fac = Factory.getInstance();
        this.em = fac.getManager();
    }

    @Override
    public String getUsersAsJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getOneUserAsJSON(long user_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Expects just ONE object, not an array of objects
     *
     * @param json
     * @return
     */
    @Override
    public UserInfo createUserFromJSON(String json) {
        UserInfo user = gson.fromJson(json, UserInfo.class);

        try {
            user.setPassword(HashCreator.createHash(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
        }

        return null;
    }

    @Override
    public UserInfo editUser(String json, long user_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserInfo deleteUser(long user_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
