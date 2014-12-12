package facadeTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.UserFacade;
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

    private UserFacadeInterface instance;
    private GsonBuilder gsonBuilder;
    private Gson transformer;

    private UserInfo u1, u2, u3;

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
        instance = new UserFacade(transformer);
        //instance = new UserFacadeMock(transformer);
        insertDummyData();
    }

    @After
    public void tearDown() {
        removeDummyData();
    }

    public void insertDummyData() {
        u1 = new UserInfo("TESTthomashed", "TESTthomas@hed.com", "bornholm");
        u2 = new UserInfo("TESTrobertelving", "TESTrobert@elving.com", "jylland");
        u3 = new UserInfo("TESTsimongroenborg", "TESTsimon@groenborg.com", "falster");

        instance.createUserFromJSON(transformer.toJson(u1));
        instance.createUserFromJSON(transformer.toJson(u2));
        instance.createUserFromJSON(transformer.toJson(u3));
    }

    public void removeDummyData() {
        instance.deleteUser(u1.getUsername());
        instance.deleteUser(u2.getUsername());
        instance.deleteUser(u3.getUsername());
    }

    /**
     * Test of getUsersAsJSON method, of class UserFacadeInterface.
     */
    @Test
    public void testGetUsersAsJSON() {
        System.out.println("getUsersAsJSON");

        String expPerson = instance.getOneUserAsJSON(u2.getUsername());
        String responseFromServer = instance.getUsersAsJSON();
        assertTrue(responseFromServer.contains(expPerson));
    }

    /**
     * Test of getOneUserAsJSON method, of class UserFacadeInterface.
     */
    @Test
    public void testGetOneUserAsJSON() {
        System.out.println("getOneUserAsJSON");
        UserInfo fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(u1.getUsername()), UserInfo.class);

        if (u1.getUsername().equals(fetchedUser.getUsername())
                && u1.getEmail().equals(fetchedUser.getEmail())) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    /**
     * Test of createUserFromJSON method, of class UserFacadeInterface.
     */
    @Test
    public void testCreateUserFromJSON() {
        System.out.println("createUserFromJSON");
        String username = "yrsa94";
        UserInfo yrsa = new UserInfo(username, "yrsa94@mailme.com", "samsø");
        instance.createUserFromJSON(transformer.toJson(yrsa));
        UserInfo fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(yrsa.getUsername()), UserInfo.class);
        assertEquals(username, fetchedUser.getUsername());

        instance.deleteUser(username);
    }

    /**
     * Test of editUser method, of class UserFacadeInterface.
     */
    @Test
    public void testEditUser() {
        System.out.println("editUser");

        //== Preface 
        String mail = "testing@testing.com";
        UserInfo userToEdit = transformer.fromJson(instance.getOneUserAsJSON(u3.getUsername()), UserInfo.class);
        userToEdit.setEmail(mail);
        instance.editUser(transformer.toJson(userToEdit), u3.getUsername());

        //== Verification
        UserInfo fetchedUser = transformer.fromJson(instance.getOneUserAsJSON(u3.getUsername()), UserInfo.class);
        assertEquals(mail, fetchedUser.getEmail());
    }

    /**
     * Test of deleteUser method, of class UserFacadeInterface.
     */
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        // Tests the return of the deleted user

        UserInfo userToDelete = new UserInfo("test2", "test2@delete.com", "falster");
        instance.createUserFromJSON(transformer.toJson(userToDelete));
        String foundUser = instance.getOneUserAsJSON(userToDelete.getUsername());
        
        instance.deleteUser(userToDelete.getUsername());
        String shouldBeNull = instance.getOneUserAsJSON(userToDelete.getUsername());
        
        if(shouldBeNull == null && foundUser != null){
            assertTrue(true);
        }else{
            assertFalse(true);
        }
        
    }

    /**
     * Test of validateUser, of class UserFacadeInterface Case 1: Create user,
     * Expect true
     *
     * Case 2: Retrieve from db, Expect false.
     */
    @Test
    public void validateUser() {
        System.out.println("validateUser");
        // Case 1
        String jsonInput = "{username: " + u1.getUsername() + ", password: " + u1.getPassword() + "}";
        boolean validated1 = instance.validateUser(jsonInput);
        assertTrue(validated1);

        // Case 2
        String notExistingUser = "{username: idontexist, password: strongpassword}";
        boolean validated2 = instance.validateUser(notExistingUser);
        assertFalse(validated2);

    }
}
