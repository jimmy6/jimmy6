

    drop table APP_PREDEFINED_VALUE cascade constraints;

    drop table APP_USER cascade constraints;

    drop table APP_USER_IN_ROLE cascade constraints;

    drop table APP_USER_ROLE cascade constraints;

    create table APP_PREDEFINED_VALUE (
        type varchar2(31) not null,
        code varchar2(20) not null,
        flag varchar2(20),
        description varchar2(50) not null,
        primary key (code)
    );

    create table APP_USER (
        username varchar2(50) not null,
        ADD_BY varchar2(50),
        DATETIME_ADD date,
        UPDATE_BY varchar2(255),
        DATETIME_UPDATE date,
        version number(10,0) not null,
        name varchar2(50) not null,
        password varchar2(32) not null,
        enabled number(1,0) not null,
        email varchar2(50) not null,
        dob date not null,
        primary key (username)
    );

    create table APP_USER_IN_ROLE (
        fk_user varchar2(50) not null,
        fk_user_role varchar2(50) not null,
        primary key (fk_user, fk_user_role)
    );

    create table APP_USER_ROLE (
        role_name varchar2(50) not null,
        ADD_BY varchar2(50),
        DATETIME_ADD date,
        UPDATE_BY varchar2(255),
        DATETIME_UPDATE date,
        version number(10,0) not null,
        status number(1,0),
        role_description varchar2(100) not null,
        role_activity varchar2(255) not null,
        primary key (role_name)
    );

    alter table APP_USER_IN_ROLE 
        add constraint FK5ADEC354BB804956 
        foreign key (fk_user) 
        references APP_USER;

    alter table APP_USER_IN_ROLE 
        add constraint FK5ADEC354A9C56BD7 
        foreign key (fk_user_role) 
        references APP_USER_ROLE;

INSERT INTO APP_predefined_value (code, type, description, flag) VALUES 
  ('ACT_USERADM','Role_Activity','User Admimistrator','1');

INSERT INTO APP_predefined_value (code, type, description, flag) VALUES 
  ('ROLE_USER','Role_Activity','Account','0');

INSERT INTO APP_user (version,dob ,DATETIME_UPDATE, email, name, enabled, password, username, DATETIME_ADD, ADD_BY, UPDATE_BY) VALUES 
  (1,SYSDATE,SYSDATE,'dtsb@yahoo.com','DTSB',1,'5f4dcc3b5aa765d61d8327deb882cf99','SUREWIN',SYSDATE,'DEFAULT',NULL);

INSERT INTO APP_user_role (version, DATETIME_UPDATE, role_name, status, role_activity, role_description, DATETIME_ADD, ADD_BY, UPDATE_BY) VALUES 
  (1,SYSDATE,'ADMIN',0,
  'ROLE_USER;ACT_USERADM','Have all the privilege',SYSDATE,'DEFAULT',null);

INSERT INTO APP_user_in_role (fk_user, fk_user_role) VALUES 
  ('SUREWIN','ADMIN');
 
  commit;