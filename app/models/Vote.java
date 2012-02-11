package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Vote extends Model {
    @ManyToOne
    public User user;
    @ManyToOne
    public Topic topic;
    public Date createdAt;
    public int value;

    public Vote(User user, Topic topic, int value) {
        this.user = user;
        this.topic = topic;
        this.value = value;
        this.createdAt = new Date();
    }
}
