package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Vote extends Model {
    @ManyToOne
    public User user;
    @ManyToOne
    public Topic topic;
    public Date createdAt;

    public Vote(User user, Topic topic) {
        this.user = user;
        this.topic = topic;
        this.createdAt = new Date();
    }
}
