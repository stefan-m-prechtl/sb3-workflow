CREATE TABLE IF NOT EXISTS restdemo.t_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(10),
    firstname VARCHAR(50),
    lastname VARCHAR(50)
);
