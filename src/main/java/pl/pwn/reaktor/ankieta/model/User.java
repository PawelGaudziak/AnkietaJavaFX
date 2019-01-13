package pl.pwn.reaktor.ankieta.model;


import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum  role;

    private Boolean active;

    public User() {
    }

    public User(String login, String password, RoleEnum role, Boolean active) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User(long id, String login, String password, RoleEnum role, Boolean active) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", role=" + role +
               ", active=" + active +
               '}';
    }
}
