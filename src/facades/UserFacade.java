package facades;

import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.UserInfo;
import webServer.Factory;
import webServer.HashCreator;
import webinterfaces.UserFacadeInterface;

public class UserFacade implements UserFacadeInterface {

    private Gson transformer;
    private EntityManager em;
    private Factory fac;

    public UserFacade(Gson transformer) {
        this.transformer = transformer;
        this.fac = Factory.getInstance();
        this.em = fac.getManager();
    }

    @Override
    public String getUsersAsJSON() {
        List<UserInfo> result = em.createQuery("SELECT u FROM UserInfo u").getResultList();
        return transformer.toJson(result);
    }

    @Override
    public String getOneUserAsJSON(String username) {
        Query qu = em.createQuery("SELECT u FROM UserInfo u WHERE u.username = :arg").setParameter("arg", username);
        UserInfo userFromDb = (UserInfo) qu.getSingleResult();
        return transformer.toJson(userFromDb);
    }

    @Override
    public boolean validateUser(String json) {
        UserInfo userFromJson = transformer.fromJson(json, UserInfo.class);

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
        UserInfo userFromJson = transformer.fromJson(json, UserInfo.class);

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
    public UserInfo editUser(String json, String username) {
<<<<<<< HEAD
        UserInfo editedUser = transformer.fromJson(json, UserInfo.class);
        Query qu = em.createQuery("SELECT u FROM UserInfo u WHERE u.username = :arg").setParameter("arg", username);
        UserInfo userToEdit = (UserInfo) qu.getSingleResult();
        em.getTransaction().begin();
        userToEdit.setUsername(editedUser.getUsername());
        userToEdit.setEmail(editedUser.getEmail());
        userToEdit.setPassword(editedUser.getPassword());
        em.getTransaction().commit();
        return userToEdit;
=======
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
>>>>>>> FETCH_HEAD
    }

    @Override
    public UserInfo deleteUser(String username) {
<<<<<<< HEAD
        em.getTransaction().begin();
        Query qu = em.createQuery("SELECT u FROM UserInfo u WHERE u.username = :arg").setParameter("arg", username);
        UserInfo userFromDb = (UserInfo) qu.getSingleResult();
        em.remove(userFromDb);
        em.getTransaction().commit();
        return userFromDb;
=======
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
>>>>>>> FETCH_HEAD
    }

}
