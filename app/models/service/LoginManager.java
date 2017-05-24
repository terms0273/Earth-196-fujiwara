/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.service;

import models.dao.Account;
import models.dto.LoginRequest;
import org.mindrot.jbcrypt.BCrypt;

/**
 * ログイン処理用ビジネスロジッククラス
 * @author y-fujiwara
 */
public class LoginManager {
    
    /**
     * ログインしようとしているユーザーチェック用メソッド
     * @param req リクエストパラメータバインドオブジェクト
     * @return ログインユーザーモデル 
     */
    public Account chkUser(LoginRequest req) {
        Account user = Account.find.where().eq("userName",req.loginId).findUnique();
        // 1件もなかったら即終了
        if (user == null) {
            return null;
        }
        // 1件あったらパスワードチェック
        else if (BCrypt.checkpw(req.password, user.password)) {
            // 認証成功
            return user;
        }
        return null;
    }
}
