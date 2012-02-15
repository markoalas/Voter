package controllers;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.Validation;

public class Topic extends SecureController {
    public static void addTopic() {
        render();
    }

    public static void saveTopic(@Required @MaxSize(255) String topicTitle, String description, @MaxSize(255) String proposedSpeaker) {
        if (Validation.hasErrors()) {
            params.flash();
            Validation.keep();
            addTopic();
        }

        new models.Topic(getConnectedUser(), topicTitle, description, proposedSpeaker).save();
        Application.index();
    }
}
