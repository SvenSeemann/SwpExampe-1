package user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by justusadam on 09/12/14.
 */
@Entity
public class Role {

    @GeneratedValue
    @Id
    private long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
