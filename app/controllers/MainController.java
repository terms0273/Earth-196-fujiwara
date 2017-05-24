package controllers;

import models.auth.LoginSecured;
import play.mvc.*;

public class MainController extends Controller {

    @Security.Authenticated(LoginSecured.class)
    public static Result index() {
        return ok("It works!");
    }

}