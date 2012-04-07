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
username varchar(50) not null unique,
school varchar(50)
);

/* create table for friendship */
CREATE TABLE Friend (
accountId1 int,
accountId2 int,
CONSTRAINT FOREIGN KEY (accountId1) REFERENCES Account(accountId),
CONSTRAINT FOREIGN KEY (accountId2) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (accountId1, accountId2));

/* create table for request "be friend" */
CREATE TABLE Request (
targetId int,
requestId int,
CONSTRAINT FOREIGN KEY (targetId) REFERENCES Account(accountId),
CONSTRAINT FOREIGN KEY (requestId) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (targetId, requestId));
