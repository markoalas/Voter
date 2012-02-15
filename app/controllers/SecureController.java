package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class SecureController extends Controller {
    @Before
    static void setConnectedUser() {
        if (Secure.Security.isConnected()) {
            renderArgs.put("user", getConnectedUser().name);
        }
    }

    protected static User getConnectedUser() {
        return User.find("byName", Secure.Security.connected()).first();
    }
}
