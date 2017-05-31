/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.auth;

import controllers.routes;
import play.mvc.Http;
import play.mvc.Result;
import static play.mvc.Results.redirect;
import play.mvc.Security;

/**
 * ログインチェック用クラス
 * @author y-fujiwara
 */
public class LoginSecured  extends Security.Authenticator{
    
    @Override
    public String getUsername(Http.Context ctx){
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx){
        return redirect(routes.SessionController.login());
    }
}
