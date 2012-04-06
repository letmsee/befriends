DROP DATABASE IF EXISTS befriends;

CREATE DATABASE befriends;

USE befriends;

/* create table for account */
CREATE TABLE Account (
accountId int primary key auto_increment,
birthday date,
emailAddress varchar(50) not null unique,
gender varchar(6),
password varchar(20),
username varchar(50) not null unique );