insert into user values ('1b80114c', 'fred', 'Fred'); 
insert into user values ('cf66dae3', 'wilma', 'Wilma'); 
insert into user values ('a8b9800d', 'barney', 'Barney'); 
insert into user values ('66223e28', 'betty', 'Betty'); 

-- SQL queries 
-- select * from user where username = 'fred'; 
-- insert into user values ('', '', ''); 

select * from user; 
select * from task; 

select * from user 
left join task on task.user_id = user.user_id; 