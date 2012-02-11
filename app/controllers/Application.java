package controllers;

import play.data.validation.Required;
import play.mvc.Controller;

import static play.data.validation.Validation.hasErrors;

public class Application extends Controller {

    public static void index() {
        render();
    }


    public static void sayHello(@Required String myName) {
        if (hasErrors()) {
            flash.error("Oops, please enter your name!");
            index();
        }

        render(myName);
    }
}