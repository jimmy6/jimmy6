
    alter table APP_USER_IN_ROLE 
        drop 
        foreign key FKC9139C5A90D2BAC8;

    alter table APP_USER_IN_ROLE 
        drop 
        foreign key FKC9139C5A7408FA57;

    drop table if exists APP_PREDEFINED_VALUE;

    drop table if exists APP_USER;

    drop table if exists APP_USER_IN_ROLE;

    drop table if exists APP_USER_ROLE;

    create table APP_PREDEFINED_VALUE (
        type varchar(31) not null,
        code varchar(20) not null,
        flag varchar(20),
        description varchar(50) not null,
        primary key (code)
    );

    create table APP_USER (
        user_id integer not null auto_increment,
        ADD_BY integer,
        DATETIME_ADD datetime,
        UPDATE_BY integer,
        DATETIME_UPDATE datetime,
        version integer not null,
        username varchar(50),
        name varchar(50) not null,
        password varchar(32) not null,
        enabled bit not null,
        email varchar(50) not null,
        dob datetime not null,
        primary key (user_id)
    );

    create table APP_USER_IN_ROLE (
        fk_user_id integer not null,
        fk_user_role varchar(50) not null,
        primary key (fk_user_id, fk_user_role)
    );

    create table APP_USER_ROLE (
        role_name varchar(50) not null,
        ADD_BY integer,
        DATETIME_ADD datetime,
        UPDATE_BY integer,
        DATETIME_UPDATE datetime,
        version integer not null,
        status bit,
        role_description varchar(100) not null,
        role_activity varchar(255) not null,
        primary key (role_name)
    );

    alter table APP_USER_IN_ROLE 
        add index FKC9139C5A90D2BAC8 (fk_user_role), 
        add constraint FKC9139C5A90D2BAC8 
        foreign key (fk_user_role) 
        references APP_USER_ROLE (role_name);

    alter table APP_USER_IN_ROLE 
        add index FKC9139C5A7408FA57 (fk_user_id), 
        add constraint FKC9139C5A7408FA57 
        foreign key (fk_user_id) 
        references APP_USER (user_id);

        
INSERT INTO APP_predefined_value (code, type, description, flag) VALUES
  ('ACT_USERADM','Role_Activity','User Admimistrator','1');

INSERT INTO APP_predefined_value (code, type, description, flag) VALUES
  ('ROLE_USER','Role_Activity','Account','0');

INSERT INTO APP_user (version,dob ,DATETIME_UPDATE, email, name, enabled, password, username, DATETIME_ADD, ADD_BY, UPDATE_BY) VALUES
  (1,'2006-04-12 20:25:30','2006-04-12 20:25:30','dtsb@yahoo.com','DTSB',1,'5f4dcc3b5aa765d61d8327deb882cf99','SUREWIN','2006-04-12 20:25:30',NULL,NULL);

INSERT INTO APP_user_role (version, DATETIME_UPDATE, role_name, status, role_activity, role_description, DATETIME_ADD, ADD_BY, UPDATE_BY) VALUES
  (1,'2006-04-12 20:25:30','ADMIN',1,
  'ROLE_USER;ACT_USERADM','Have all the privilege','2006-04-12 20:25:30',null,null);

INSERT INTO APP_user_in_role (fk_user_id, fk_user_role) VALUES
  (1,'ADMIN');
