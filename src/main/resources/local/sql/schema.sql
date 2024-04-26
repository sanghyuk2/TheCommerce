DROP TABLE IF EXISTS User;

CREATE TABLE User (
    userid BIGINT PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(100) NOT NULL UNIQUE,
    phonenumber VARCHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    join_date DATETIME NOT NULL,
    updated_date DATETIME NOT NULL
);