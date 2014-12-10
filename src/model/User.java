package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author christophermortensen
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdGen")
    @SequenceGenerator(name = "userIdGen", sequenceName = "USER_SEQ", initialValue = 100000, allocationSize = 1)
    @Column(name = "USER_ID")
    private Long user_id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SALT", unique = true)
    private String salt;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Long user_id, String username, String email, String password, String salt) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", username=" + username + ", email=" + email + ", password=" + password + '}';
    }

}
