CREATE SCHEMA IF NOT EXISTS restdemo;

CREATE TABLE IF NOT EXISTS restdemo.t_users (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50)
);