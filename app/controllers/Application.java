package controllers;

import models.Topic;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        List<Topic> topics = Topic.find("order by createdAt desc").fetch();
        render(topics);
    }

    public static void topic(Long id) {
        Topic topic = Topic.findById(id);
        render(topic);
    }
    
    public static void voteUp(Long id) {
        Topic topic = Topic.findById(id);
        User user = User.find("byName", "Bob").first();
        topic.votedBy(user, 1);
        index();
    }
    
    public static void voteDown(Long id) {
        Topic topic = Topic.findById(id);
        User user = User.find("byName", "Bob").first();
        topic.votedBy(user, -1);
        index();
    }

//    public static void sayHello(@Required String myName) {
//        if (hasErrors()) {
//            flash.error("Oops, please enter your name!");
//            index();
//        }
//
//        render(myName);
//    }
}