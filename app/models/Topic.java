package models;

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
    @ManyToOne
    public User author;

    public Topic(User author, String title, String description, String proposedSpeaker) {
        this.title = title;
        this.description = description;
        this.proposedSpeaker = proposedSpeaker;
        this.createdAt = new Date();
        this.votes = new ArrayList<Vote>();
        this.author = author;
    }

    public int getScore() {
        int total = 0;
        for (Vote vote : votes) {
            total += vote.value;
        }

        return total;
    }

    public void votedBy(User user, Vote.Direction direction) {
        VoteHandler.vote(user, this, direction);
    }
}
