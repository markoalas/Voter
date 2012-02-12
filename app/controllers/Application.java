package controllers;

import models.Topic;
import models.User;
import models.Vote;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

import static models.Vote.Direction.DOWN;
import static models.Vote.Direction.UP;

@With(Secure.class)
public class Application extends Controller {
    public static void index() {
        List<Topic> topics = Topic.find("order by createdAt desc").fetch();
        render(topics);
    }

    public static void addTopic() {
        render();
    }

    public static void saveTopic(String title, String description, String proposedSpeaker) {
        new Topic(getConnectedUser(), title, description, proposedSpeaker).save();
        index();
    }

    public static void topic(Long id) {
        Topic topic = Topic.findById(id);
        render(topic);
    }

    public static void voteUp(Long id) {
        vote(id, UP);
        index();
    }

    public static void voteDown(Long id) {
        vote(id, DOWN);
        index();
    }

    private static void vote(Long id, Vote.Direction direction) {
        Topic topic = Topic.findById(id);
        topic.votedBy(getConnectedUser(), direction);
    }

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            renderArgs.put("user", getConnectedUser().name);
        }
    }

    private static User getConnectedUser() {
        return User.find("byName", Security.connected()).first();
    }
}