package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by lubuntu on 10/22/16.
 */
@Entity
public class User extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    public Long id;
    public String password;
    public String email;
    @OneToOne
    public Profile profile;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @ManyToMany
    @JoinTable(name = "user_connection",
            joinColumns =
                    {@JoinColumn(
                                name = "user_id")},
            inverseJoinColumns =
                    {
                            @JoinColumn(
                                    name = "connection_id")})


    public Set<User> connections;
    @OneToMany(mappedBy = "sender")
    public List<ConnectionRequest> connectionRequestSent;
    @OneToMany(mappedBy = "receiver")
    public List<ConnectionRequest> connectionRequestReceived;

    public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(User.class);

}