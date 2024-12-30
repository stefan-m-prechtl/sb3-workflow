-- salt $2a$10$ +<22-Zeichen>
INSERT INTO restdemo.t_users (username, hashedpwd,firstname, lastname) VALUES ('admin',crypt('geheim123', '$2a$10$abcdefghijklmnopqrstuv'),'Erwin', 'Maier');
INSERT INTO restdemo.t_users (username, hashedpwd,firstname, lastname) VALUES ('write',crypt('geheim123', '$2a$10$abcdefghijklmnopqrstuv'),'Anna', 'Schmidt');
INSERT INTO restdemo.t_users (username, hashedpwd,firstname, lastname) VALUES ('read',crypt('geheim123', '$2a$10$abcdefghijklmnopqrstuv'),'Eva', 'Huber');

INSERT INTO restdemo.t_globalroles (rolename, description) VALUES ('ADMIN', 'Rolle für Administatoren');
INSERT INTO restdemo.t_globalroles (rolename, description) VALUES ('READ', 'Rolle für Lesezugriff');
INSERT INTO restdemo.t_globalroles (rolename, description) VALUES ('WRITE', 'Rolle für Schreibzugriff');

-- Rollen für Admin
INSERT INTO restdemo.t_users2globalroles(userid, roleid) 
VALUES (
    (SELECT id FROM restdemo.t_users WHERE username = 'admin'),
    (SELECT id FROM restdemo.t_globalroles WHERE rolename = 'ADMIN')
);

INSERT INTO restdemo.t_users2globalroles(userid, roleid) 
VALUES (
    (SELECT id FROM restdemo.t_users WHERE username = 'admin'),
    (SELECT id FROM restdemo.t_globalroles WHERE rolename = 'WRITE')
);

INSERT INTO restdemo.t_users2globalroles(userid, roleid) 
VALUES (
    (SELECT id FROM restdemo.t_users WHERE username = 'admin'),
    (SELECT id FROM restdemo.t_globalroles WHERE rolename = 'READ')
);

-- Rollen für Writer

INSERT INTO restdemo.t_users2globalroles(userid, roleid) 
VALUES (
    (SELECT id FROM restdemo.t_users WHERE username = 'write'),
    (SELECT id FROM restdemo.t_globalroles WHERE rolename = 'WRITE')
);

INSERT INTO restdemo.t_users2globalroles(userid, roleid) 
VALUES (
    (SELECT id FROM restdemo.t_users WHERE username = 'write'),
    (SELECT id FROM restdemo.t_globalroles WHERE rolename = 'READ')
);

-- Rolle für Leser
INSERT INTO restdemo.t_users2globalroles(userid, roleid) 
VALUES (
    (SELECT id FROM restdemo.t_users WHERE username = 'read'),
    (SELECT id FROM restdemo.t_globalroles WHERE rolename = 'READ')
);