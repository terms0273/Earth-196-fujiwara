package controllers;


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
        
        return redirect(routes.AccountController.index());
    }
    
    /**
     * ログアウトアクションメソッド
     * @return 
     */
    public static Result logout() {
        deleteSession();
        return redirect(routes.SessionController.login());
    }
    
    /**
     * セッション作成メソッド
     */
    private static void createSession(Account user) {
        session("id", user.id.toString());
        session("type", user.type.toString());
        session("username", user.userName);
    }
    
    /**
     * セッション破棄メソッド
     */
    private static void deleteSession() {
        session().clear();        
    }
    
    

}