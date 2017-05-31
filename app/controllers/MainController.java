package controllers;

import models.auth.LoginSecured;
import play.mvc.*;
import views.html.*;

public class MainController extends Controller {

    @Security.Authenticated(LoginSecured.class)
    public static Result index() {
        return ok(index.render("MAIN"));
    }

}