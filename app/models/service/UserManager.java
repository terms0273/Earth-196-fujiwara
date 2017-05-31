/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.service;

import java.util.List;
import models.dao.Account;
import play.Logger;

/**
 *
 * @author y-fujiwara
 */
public class UserManager {
    
    /**
     * DB内で削除されていないユーザーを全部取得するメソッド
     * @return 取得したユーザーリスト
     */
    public List<Account> getAll() {
        return Account.find.where().ne("isDelete", 0).findList();
    }
    
    /**
     * ログイン中のユーザーIDに紐づくプライマリーキーを取得するメソッド
     * @param username セッションに入っているユーザーID
     * @return ユーザーIDに紐づくプライマリーキー
     */
    public Long getId(String username) {
        return Account.find.where().eq("userName", username).findUnique().id;
    }
    
    public Integer getType(String username) {
        return Account.find.where().eq("userName", username).findUnique().type;
    }
    
    public Account getUser(String username) {
        return Account.find.where().eq("userName", username).findUnique();
    }
    
    /**
     * 論理削除実行メソッド
     * @param id 削除対象ID
     * @return true: 削除成功 false: 削除失敗
     */
    public boolean delete(Long id) {
        Account target = Account.find.byId(id);
        boolean ret = true;
        try {
            target.isDelete = 0;
            target.save();
        } catch(Exception ex) {
            Logger.info(ex.getMessage());
            ret = false;
        }
        return ret;        
    }
}
