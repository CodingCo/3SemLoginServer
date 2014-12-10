package facadeTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.UserInfo;
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
//    @Test
//    public void testGetUsersAsJSON() {
//        System.out.println("getUsersAsJSON");
//        
//        UserInfo u1 = new UserInfo("thomashed", "thomas@hed.com", "bornholm");
//        UserInfo u2 = new UserInfo("robertelving", "robert@elving.com", "jylland");
//        UserInfo u3 = new UserInfo("simongroenborg", "simon@groenborg.com", "falster");
//        
//        instance.createUserFromJSON(transformer.toJson(u1));
//        instance.createUserFromJSON(transformer.toJson(u2));
//        instance.createUserFromJSON(transformer.toJson(u3)); 
//        
//        String expPerson = instance.getOneUserAsJSON(100001);
//        String responseFromServer = instance.getUsersAsJSON();
//        assertTrue(responseFromServer.contains(expPerson));
//    }
//
//    /**
//     * Test of getOneUserAsJSON method, of class UserFacadeInterface.
//     */
//    @Test
//    public void testGetOneUserAsJSON() {
//        System.out.println("getOneUserAsJSON");
//        UserInfo u1 = new UserInfo("thomashed", "thomas@hed.com", "bornholm");
//        String p1AsJSON = transformer.toJson(u1, UserInfo.class);
//        instance.createUserFromJSON(p1AsJSON);
//        UserInfo fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(100000), UserInfo.class);
//        
//        if (fetchedUser.getUser_id() == 100000 && u1.getUsername().equals(fetchedUser.getUsername())
//                && u1.getEmail().equals(fetchedUser.getEmail())){
//            assertTrue(true);
//        } else{
//            assertTrue(false);
//        }
//    }
//
//    /**
//     * Test of createUserFromJSON method, of class UserFacadeInterface.
//     */
//    @Test
//    public void testCreateUserFromJSON() {
//        System.out.println("createUserFromJSON");
//        UserInfo u1 = new UserInfo("yrsa94", "yrsa94@mailme.com", "samsø");
//        instance.createUserFromJSON(transformer.toJson(u1));
//        long user_id = 100000;
//        UserInfo fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(user_id), UserInfo.class);
//        assertEquals(user_id, (long) fetchedUser.getUser_id());
//    }
//
//    /**
//     * Test of editUser method, of class UserFacadeInterface.
//     */
//    @Test
//    public void testEditUser() {
//        System.out.println("editUser");
//        
//        //== Preface 
//        long user_id = 100000L;
//        String newUserName = "rudolf";
//        UserInfo u1 = new UserInfo("simong", "simon@g.com", "stodag");
//        instance.createUserFromJSON(transformer.toJson(u1));
//        UserInfo userToEdit = transformer.fromJson(instance.getOneUserAsJSON(user_id), UserInfo.class);
//        userToEdit.setUsername(newUserName);
//        instance.editUser(transformer.toJson(userToEdit), userToEdit.getUser_id());
//        
//        //== Verification
//        UserInfo fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(user_id), UserInfo.class);
//        assertEquals(newUserName, fetchedUser.getUsername());
//    }
//
//    /**
//     * Test of deleteUser method, of class UserFacadeInterface.
//     */
//    @Test
//    public void testDeleteUser() {
//        System.out.println("deleteUser");
//        long user_id = 100000L;
//        UserInfo u1 = new UserInfo("kuftesthundt", "kuftest@hundt.com", "kh");
//        
//        instance.createUserFromJSON(transformer.toJson(u1));
//        String expResultJSON = instance.getOneUserAsJSON(user_id);
//        String deletedUserJSON = transformer.toJson(instance.deleteUser(user_id));
//        assertEquals(expResultJSON, deletedUserJSON);
//    }

}
