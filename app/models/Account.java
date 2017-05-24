/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import play.db.ebean.Model;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "accounts")
public class Account extends Model {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Column(unique=true)
    public String userName;

    @NotNull
    public String password;

    @NotNull
    public String fullName;

    @Column(columnDefinition = "integer default 1")
    public Integer type = 1;

    @Column(columnDefinition = "integer default 1")
    public Integer isDelete = 1;

    public static Finder<Long, Account> find = new Finder<Long, Account>(Long.class, Account.class);
}

