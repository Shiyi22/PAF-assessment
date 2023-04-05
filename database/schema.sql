drop database if exists todo;

create database todo;
use todo; 

create table user (
	user_id varchar(8) not null,
    username varchar(15) not null,
    name varchar(15),
	constraint user_pk primary key(user_id) 
); 
