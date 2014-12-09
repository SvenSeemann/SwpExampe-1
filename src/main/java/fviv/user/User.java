package fviv.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by justusadam on 09/12/14.
 */
@Entity
public class User {
    @GeneratedValue
    @Id
    private long id;

    private String firstname;

    private String surname;

    @OneToMany
    private List<Role> roles;

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
