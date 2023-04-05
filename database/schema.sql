drop database if exists todo;

create database todo;
use todo; 

create table user (
	user_id varchar(8) not null,
    username varchar(15) not null,
    name varchar(15),
	constraint user_pk primary key(user_id) 
); 

-- Task 5 
create table task (
	task_id int auto_increment, 
    user_id varchar(8) not null, 
    description varchar(255), 
    priority int, 
    due_date Date,
    constraint task_pk primary key(task_id),
    constraint task_user_fk foreign key(user_id) references user(user_id)
); 