package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends Model {
    public String name;
    public String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Vote> votes;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    public List<Topic> topics;

    public User(String name, String password) {
        this.name = name;
        this.password = password;

        votes = new ArrayList<Vote>();
        topics = new ArrayList<Topic>();
    }

    public static User connect(String name, String password) {
        return find("byNameAndPassword", name, password).first();
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
        save();
    }
}
