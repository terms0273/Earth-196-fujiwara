# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accounts (
  id                        bigint auto_increment not null,
  user_name                 varchar(255) not null,
  password                  varchar(255) not null,
  full_name                 varchar(255) not null,
  type                      integer default 1,
  is_delete                 integer default 1,
  constraint uq_accounts_user_name unique (user_name),
  constraint pk_accounts primary key (id))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists accounts;

SET REFERENTIAL_INTEGRITY TRUE;

