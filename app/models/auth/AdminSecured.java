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
 *
 * @author y-fujiwara
 */
public class AdminSecured extends Security.Authenticator{
    
    @Override
    public String getUsername(Http.Context ctx){
        String type = ctx.session().get("type");
        // セッションにtypeが入ってないかadminユーザーでなければセキュリティを掛ける
        if(type == null || !type.equals(0)) {
            return null;
        }
        return ctx.session().get("id");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx){
        return redirect(routes.MainController.index());
    }
}
