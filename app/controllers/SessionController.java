package controllers;


import models.auth.LoginSecured;
import models.dao.Account;
import models.dto.LoginRequest;
import models.service.LoginManager;
import static play.data.Form.form;
import play.mvc.*;
import views.html.*;

/**
 * ログイン/ログアウトを行うコントローラー
 * @author y-fujiwara
 */
public class SessionController extends Controller {

    /**
     * ログイン画面表示アクションメソッド
     * @return 
     */
    public static Result login() {
        return ok(login.render("LOGIN PAGE", form(LoginRequest.class)));
    }
    
    /**
     * ログインデータPOSTアクションメソッド
     * @return 
     */
    public static Result doLogin() {
        LoginRequest loginReq = form(LoginRequest.class).bindFromRequest().get();
        LoginManager manager = new LoginManager();
        Account user = manager.chkUser(loginReq);
        
        if(user == null) {
            flash("message", "ログイン情報が不正です");
            return redirect(routes.SessionController.login());
        }
        createSession(user);
        
        return redirect(routes.MainController.index());
    }
    
    /**
     * ログアウトアクションメソッド
     * @return 
     */
    @Security.Authenticated(LoginSecured.class)
    public static Result logout() {
        deleteSession();
        return redirect(routes.SessionController.login());
    }
    
    /**
     * セッション作成メソッド
     */
    private static void createSession(Account user) {
        session("username", user.userName);
    }
    
    /**
     * セッション破棄メソッド
     */
    private static void deleteSession() {
        session().clear();        
    }
    
    

}