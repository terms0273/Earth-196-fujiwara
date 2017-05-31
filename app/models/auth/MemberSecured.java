/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.auth;

import controllers.routes;
import models.dao.Account;
import play.mvc.Http;
import play.mvc.Result;
import static play.mvc.Results.redirect;
import play.mvc.Security;

/**
 *
 * @author y-fujiwara
 */
public class MemberSecured extends Security.Authenticator{
    
    /**
     * クエリパラメータとセッション情報が同一かチェックするメソッド
     * @param ctx
     * @return 
     */
    @Override
    public String getUsername(Http.Context ctx) {
        String username = ctx.session().get("username");
        Long id = Account.find.where().eq("userName", username).findUnique().id;
        
        if(ctx.request().getQueryString("id").equals(id.toString())) {
            return "OK";
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx){
        return redirect(routes.MainController.index());
    }
}
