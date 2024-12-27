-- salt $2a$10$ +<22-Zeichen>
INSERT INTO restdemo.t_users (username, hashedpwd,firstname, lastname) VALUES ('emai',crypt('geheim123', '$2a$10$abcdefghijklmnopqrstuv'),'Erwin', 'Maier');
INSERT INTO restdemo.t_users (username, hashedpwd,firstname, lastname) VALUES ('asch',crypt('geheim123', '$2a$10$abcdefghijklmnopqrstuv'),'Anna', 'Schmidt');