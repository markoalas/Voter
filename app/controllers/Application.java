package controllers;

import models.Topic;
import models.User;
import models.Vote;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

import static models.Vote.Direction.DOWN;
import static models.Vote.Direction.UP;

@With(Secure.class)
public class Application extends Controller {
    public static void index() {
        List<Topic> topics = Topic.find("order by score desc").fetch();
        render(topics);
    }

    public static void addTopic() {
        render();
    }

    public static void saveTopic(@Required @MaxSize(255) String topicTitle, String description, @MaxSize(255) String proposedSpeaker) {
        System.out.println(topicTitle);
        if (Validation.hasErrors()) {
            System.out.println(Validation.errors().get(0).getKey());
            params.flash();
            Validation.keep();
            addTopic();
        }

        new Topic(getConnectedUser(), topicTitle, description, proposedSpeaker).save();
        index();
    }

    public static void topic(Long id) {
        Topic topic = Topic.findById(id);
        render(topic);
    }

    public static void voteUp(Long topicId) {
        vote(topicId, UP);
    }

    public static void voteDown(Long topicId) {
        vote(topicId, DOWN);
    }

    private static void vote(Long topicId, Vote.Direction direction) {
        Topic topic = Topic.findById(topicId);
        topic.votedBy(getConnectedUser(), direction);

        renderJSON(topic.score);
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