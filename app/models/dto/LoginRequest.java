/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.dto;

/**
 * ログインFormからのデータバインド用Beanクラス
 * @author y-fujiwara
 */
public class LoginRequest {

    /**
     * 入力されたログインID
     */
    
    public String loginId;

    /**
     * 入力されたパスワード
     */
    public String password;

}
