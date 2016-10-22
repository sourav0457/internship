package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by lubuntu on 10/22/16.
 */
@Entity
public class Profile extends Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String firstname;
    public String lastname;
    public String company;

    public Profile(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static Model.Finder<Long, Profile> find = new Model.Finder<Long, Profile>(Profile.class);
}
