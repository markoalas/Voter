package controllers;

import models.Topic;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.List;

import static play.data.validation.Validation.hasErrors;

public class Application extends Controller {

    public static void index() {
        List<Topic> topics = Topic.find("order by createdAt desc").fetch();
        render(topics);
    }


    public static void sayHello(@Required String myName) {
        if (hasErrors()) {
            flash.error("Oops, please enter your name!");
            index();
        }

        render(myName);
    }
}