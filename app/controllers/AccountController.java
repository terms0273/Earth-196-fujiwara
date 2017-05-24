package controllers;

import models.auth.AdminSecured;
import models.auth.LoginSecured;
import models.dto.DeleteRequest;
import models.dto.UserUpdateRequest;
import models.dto.PasswordUpdateRequest;
import models.service.UserManager;
import static play.data.Form.form;
import play.mvc.*;
import views.html.*;

/**
 * ユーザー関係の処理を行うコントローラー
 * @author y-fujiwara
 */
@Security.Authenticated(LoginSecured.class)
public class AccountController extends Controller {

    /**
     * ユーザー一覧表示アクションメソッド
     * @return ユーザー一覧ページHTML
     */
    public static Result index() {
        Long id =Long.parseLong(session("id"));
        UserManager manager = new UserManager();
        return ok(userIndex.render("USER INDEX", id, manager.getAll()));
    }
    
    /**
     * edit画面表示用アクションメソッド
     * @param id 編集対象ユーザーID
     * @return エディットページHTML
     */
    public static Result edit(Long id) {
        return ok(edit.render("EDIT PAGE", id, form(UserUpdateRequest.class), form(PasswordUpdateRequest.class)));
    } 
    
    /**
     * edit実行用アクションメソッド
     * @param id edit対象ユーザーID
     * @return AccountController.indexへのリダイレクト
     */
    public static Result update(Long id) {
        return ok("aaa");
    }
    
    /**
     * パスワード変更実行用アクションメソッド
     * @param id edit対象ユーザーID
     * @return SessionController.loginへのリダイレクト
     */
    public static Result passwordUpdate(Long id) {
        return ok("aaa");
    }
    
    /**
     * 削除実行用アクションメソッド
     * @param id 削除対象ユーザーID
     * @return AccountController.indexへのリダイレクト
     */
    @Security.Authenticated(AdminSecured.class)
    public static Result delete() {
        DeleteRequest deleteReq = form(DeleteRequest.class).bindFromRequest().get();
        UserManager manager = new UserManager();
        manager.delete(deleteReq.deleteId);
        return redirect(routes.AccountController.index());
    }
}