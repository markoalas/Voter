package models;

import org.hibernate.annotations.Formula;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Topic extends Model {
    public String title;
    @Lob
    public String description;
    public String proposedSpeaker;
    public Date createdAt;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    public List<Vote> votes;
    @ManyToOne(cascade = CascadeType.ALL)
    public User author;
    @Formula("select coalesce(sum(v.value), 0) from Vote v where v.topic_id = id")
    public int score;

    public Topic(User author, String title, String description, String proposedSpeaker) {
        this.title = title;
        this.description = description;
        this.proposedSpeaker = proposedSpeaker;
        this.createdAt = new Date();
        this.votes = new ArrayList<Vote>();
        this.author = author;
    }

    public void votedBy(User user, Vote.Direction direction) {
        Voter.vote(user, this, direction);
        refresh();
    }
}
