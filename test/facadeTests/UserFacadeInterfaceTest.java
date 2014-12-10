package facadeTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webinterfaces.UserFacadeInterface;
import static org.junit.Assert.*;

/**
 *
 * @author christophermortensen
 */
public class UserFacadeInterfaceTest {
    
    UserFacadeInterface instance;
    GsonBuilder gsonBuilder;
    Gson transformer;
    
    public UserFacadeInterfaceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        gsonBuilder = new GsonBuilder();
        transformer = gsonBuilder.create();
        
        //== Only one should be commented when running the test
        // instance = new UserFacadeDB(transformer);
        instance = new UserFacadeMock(transformer);
    }
    
    @After
    public void tearDown() {
    }
    // user_id
    // username
    // email
    // password
    /**
     * Test of getUsersAsJSON method, of class UserFacadeInterface.
     */
    @Test
    public void testGetUsersAsJSON() {
        System.out.println("getUsersAsJSON");
        
        User u1 = new User("thomashed", "thomas@hed.com", "bornholm");
        User u2 = new User("robertelving", "robert@elving.com", "jylland");
        User u3 = new User("simongroenborg", "simon@groenborg.com", "falster");
        
        instance.createUserFromJSON(transformer.toJson(u1));
        instance.createUserFromJSON(transformer.toJson(u2));
        instance.createUserFromJSON(transformer.toJson(u3)); 
        
        String expPerson = instance.getOneUserAsJSON(100001);
        String responseFromServer = instance.getUsersAsJSON();
        assertTrue(responseFromServer.contains(expPerson));
    }

    /**
     * Test of getOneUserAsJSON method, of class UserFacadeInterface.
     */
    @Test
    public void testGetOneUserAsJSON() {
        System.out.println("getOneUserAsJSON");
        User u1 = new User("thomashed", "thomas@hed.com", "bornholm");
        String p1AsJSON = transformer.toJson(u1, User.class);
        instance.createUserFromJSON(p1AsJSON);
        User fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(100000), User.class);
        
        if (fetchedUser.getUser_id() == 100000 && u1.getUsername().equals(fetchedUser.getUsername())
                && u1.getEmail().equals(fetchedUser.getEmail())){
            assertTrue(true);
        } else{
            assertTrue(false);
        }
    }

    /**
     * Test of createUserFromJSON method, of class UserFacadeInterface.
     */
    @Test
    public void testCreateUserFromJSON() {
        System.out.println("createUserFromJSON");
        User u1 = new User("yrsa94", "yrsa94@mailme.com", "samsø");
        instance.createUserFromJSON(transformer.toJson(u1));
        long user_id = 100000;
        User fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(user_id), User.class);
        assertEquals(user_id, (long) fetchedUser.getUser_id());
    }

    /**
     * Test of editUser method, of class UserFacadeInterface.
     */
    @Test
    public void testEditUser() {
        System.out.println("editUser");
        
        //== Preface 
        long user_id = 100000L;
        String newUserName = "rudolf";
        User u1 = new User("simong", "simon@g.com", "stodag");
        instance.createUserFromJSON(transformer.toJson(u1));
        User userToEdit = transformer.fromJson(instance.getOneUserAsJSON(user_id), User.class);
        userToEdit.setUsername(newUserName);
        instance.editUser(transformer.toJson(userToEdit), userToEdit.getUser_id());
        
        //== Verification
        User fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(user_id), User.class);
        assertEquals(newUserName, fetchedUser.getUsername());
    }

    /**
     * Test of deleteUser method, of class UserFacadeInterface.
     */
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        long user_id = 100000L;
        User u1 = new User("kuftesthundt", "kuftest@hundt.com", "kh");
        
        instance.createUserFromJSON(transformer.toJson(u1));
        String expResultJSON = instance.getOneUserAsJSON(user_id);
        String deletedUserJSON = transformer.toJson(instance.deleteUser(user_id));
        assertEquals(expResultJSON, deletedUserJSON);
    }

}
