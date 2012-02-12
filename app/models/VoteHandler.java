package models;


import static java.lang.Math.max;
import static java.lang.Math.min;

public class VoteHandler {
    public static void vote(User user, Topic topic, Vote.Direction direction) {
        Vote vote = loadExistingVote(user, topic);
        if (vote == null) {
            vote = addNewVote(user, topic, direction);
        } else {
            updateVote(vote, direction);
        }

        saveChanges(user, topic, vote);
    }

    private static Vote loadExistingVote(User user, Topic topic) {
        return Vote.find("user = ? and topic = ?", user, topic).first();
    }

    private static Vote addNewVote(User user, Topic topic, Vote.Direction direction) {
        Vote v = new Vote(user, topic, direction.value);
        topic.votes.add(v);
        user.votes.add(v);
        return v;
    }

    private static void updateVote(Vote vote, Vote.Direction direction) {
        vote.value = min(max(vote.value + direction.value, -1), 1);
    }

    private static void saveChanges(User user, Topic topic, Vote vote) {
        if (vote.value == 0) {
            deleteVote(user, topic, vote);
        }
        else {
            vote.save();
        }

        user.save();
        topic.save();
    }

    private static void deleteVote(User user, Topic topic, Vote vote) {
        user.votes.remove(vote);
        topic.votes.remove(vote);
        vote.delete();
    }
}
