DROP DATABASE IF EXISTS befriends;

CREATE DATABASE befriends;

USE befriends;

/* create table for account */
CREATE TABLE Account (
accountId int primary key auto_increment,
avatar text,
birthday date,
emailAddress varchar(50) not null unique,
gender varchar(6),
interestGender varchar(6),
password varchar(20),
status text,
username varchar(50) not null unique);

/* create table for friendship */
CREATE TABLE Friend (
accountId1 int not null,
accountId2 int not null,
CONSTRAINT FOREIGN KEY (accountId1) REFERENCES Account(accountId),
CONSTRAINT FOREIGN KEY (accountId2) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (accountId1, accountId2));

/* create table for request "be friend" */
CREATE TABLE Request (
targetId int not null,
requestId int not null,
CONSTRAINT FOREIGN KEY (targetId) REFERENCES Account(accountId),
CONSTRAINT FOREIGN KEY (requestId) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (targetId, requestId));

/* create table for denial */
CREATE TABLE Denial (
denierId int not null,
requestId int not null,
CONSTRAINT FOREIGN KEY (denierId) REFERENCES Account(accountId),
CONSTRAINT FOREIGN KEY (requestId) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (denierId, requestId));

/* create table for hobby and dislike */
CREATE TABLE Hobby (
accountId int not null,
field varchar(50) not null,
CONSTRAINT FOREIGN KEY (accountId) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (accountId, field));

CREATE TABLE Dislike(
accountId int not null,
field varchar(50) not null,
CONSTRAINT FOREIGN KEY (accountId) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (accountId, field));

/* create table for career */
CREATE TABLE Career (
accountId int not null,
school varchar(50),
job varchar(50),
CONSTRAINT FOREIGN KEY (accountId) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (accountId));

/* create table for location */
CREATE TABLE Location (
accountId int,
address varchar(50),
country varchar(50),
hometown varchar(50),
CONSTRAINT FOREIGN KEY (accountId) REFERENCES Account(accountId),
CONSTRAINT PRIMARY KEY (accountId));


