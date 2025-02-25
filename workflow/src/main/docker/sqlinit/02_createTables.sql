CREATE TABLE IF NOT EXISTS restdemo.t_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(10) NOT NULL,
    hashedpwd TEXT NOT NULL,
    firstname VARCHAR(50),
    lastname VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS restdemo.t_refreshtokens (
    id BIGSERIAL PRIMARY KEY,
    userid BIGINT NOT NULL,
    hashedpwd TEXT NOT NULL,
    token VARCHAR(36) NOT NULL,
    expirydate TIMESTAMP NOT NULL,
    FOREIGN KEY (userid) REFERENCES restdemo.t_users(id)
);


CREATE TABLE IF NOT EXISTS restdemo.t_globalroles (
    id BIGSERIAL PRIMARY KEY,
    rolename VARCHAR(25) NOT NULL,
    description VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS restdemo.t_workflowroles (
    id BIGSERIAL PRIMARY KEY,
    workflowobj UUID NOT NULL, 
    rolename VARCHAR(25) NOT NULL,
    description VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS restdemo.t_users2globalroles (
    userid BIGINT,
    roleid BIGINT,
    PRIMARY KEY (userid, roleid),
    FOREIGN KEY (userid) REFERENCES restdemo.t_users(id),
    FOREIGN KEY (roleid) REFERENCES restdemo.t_globalroles(id)
);

CREATE TABLE IF NOT EXISTS restdemo.t_users2workflowroles (
    userid BIGINT NOT NULL,
    roleid BIGINT NOT NULL,
    PRIMARY KEY (userid, roleid),
    FOREIGN KEY (userid) REFERENCES restdemo.t_users(id),
    FOREIGN KEY (roleid) REFERENCES restdemo.t_workflowroles(id)

);


