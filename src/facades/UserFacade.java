package facades;

import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    public String getOneUserAsJSON(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validateUser(String json) {
        UserInfo userFromJson = gson.fromJson(json, UserInfo.class);

        // get the user from db with the same userName
        if (userFromJson.getUsername() != null) {

            Query qu = em.createQuery("SELECT u FROM UserInfo u WHERE u.username = :arg").setParameter("arg", userFromJson.getUsername());
            UserInfo userFromDb = (UserInfo) qu.getSingleResult();

            if (userFromDb.getUsername() != null) {

                try {
                    return HashCreator.validatePassword(userFromJson.getPassword(), userFromDb.getPassword());
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                    return false;
                }
            }

        }

        return false;
    }

    /**
     * Expects just ONE object, not an array of objects
     *
     * @param json
     * @return
     */
    @Override
    public UserInfo createUserFromJSON(String json) {
        UserInfo userFromJson = gson.fromJson(json, UserInfo.class);

        try {
            userFromJson.setPassword(HashCreator.createHash(userFromJson.getPassword()));

            em.getTransaction().begin();
            em.persist(userFromJson);
            em.getTransaction().commit();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IllegalStateException ex) {
            return null;
        }

        return userFromJson;
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
