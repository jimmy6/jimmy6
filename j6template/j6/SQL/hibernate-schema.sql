
    drop table FM_PREDEFINED_VALUE cascade constraints;

    drop table FM_USER cascade constraints;

    drop table FM_USER_IN_ROLE cascade constraints;

    drop table FM_USER_ROLE cascade constraints;

    create table FM_PREDEFINED_VALUE (
        type varchar2(31) not null,
        code varchar2(20) not null,
        flag varchar2(20),
        description varchar2(50) not null,
        primary key (code)
    );

    create table FM_USER (
        username varchar2(50) not null,
        created_by varchar2(50),
        created_date date,
        modified_by varchar2(255),
        modified_date date,
        version number(10,0) not null,
        name varchar2(50) not null,
        password varchar2(32) not null,
        enabled number(1,0) not null,
        email varchar2(50) not null,
        dob date not null,
        primary key (username)
    );

    create table FM_USER_IN_ROLE (
        fk_user varchar2(50) not null,
        fk_user_role varchar2(50) not null,
        primary key (fk_user, fk_user_role)
    );

    create table FM_USER_ROLE (
        role_name varchar2(50) not null,
        created_by varchar2(50),
        created_date date,
        modified_by varchar2(255),
        modified_date date,
        version number(10,0) not null,
        status number(1,0),
        role_description varchar2(100) not null,
        role_activity varchar2(255) not null,
        primary key (role_name)
    );

    alter table FM_USER_IN_ROLE 
        add constraint FK5ADEC354BB804956 
        foreign key (fk_user) 
        references FM_USER;

    alter table FM_USER_IN_ROLE 
        add constraint FK5ADEC354A9C56BD7 
        foreign key (fk_user_role) 
        references FM_USER_ROLE;
