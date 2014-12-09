package user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by justusadam on 09/12/14.
 */
@Entity
public class User implements Serializable{
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
