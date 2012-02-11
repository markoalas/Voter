package models;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

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
        this.votes = new ArrayList<Vote>();
        this.author = author;
        this.title = title;
        this.description = description;
        this.proposedSpeaker = proposedSpeaker;
        this.createdAt = new Date();
    }

    public int getScore() {
        int total = 0;
        for (Vote vote : votes) {
            total += vote.value;
        }

        return total;
    }

    public void votedBy(User user, int value) {
        Vote v = Vote.find("user = ? and topic = ?", user, this).first();
        if (v == null) {
            v = new Vote(user, this, value);
            votes.add(v);
            user.votes.add(v);
            v.save();
        } else if (v.value == -value) {
            v.delete();
        } else {
            v.value = min(max(v.value + value, -1), 1);
            v.save();
        }

        user.save();
        save();
    }
}
