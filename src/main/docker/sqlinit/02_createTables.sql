CREATE TABLE IF NOT EXISTS restdemo.t_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(10),
    firstname VARCHAR(50),
    lastname VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS restdemo.t_globalroles (
    id SERIAL PRIMARY KEY,
    rolename VARCHAR(25),
    description VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS restdemo.t_workflowroles (
    id SERIAL PRIMARY KEY,
    workflowobj UUID, 
    rolename VARCHAR(25),
    description VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS restdemo.t_users2globalroles (
    userid SERIAL PRIMARY KEY,
    roleid SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS restdemo.t_users2workflowroles (
    userid SERIAL PRIMARY KEY,
    roleid SERIAL PRIMARY KEY
);


