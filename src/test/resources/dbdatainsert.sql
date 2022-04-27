DELETE FROM test_inside.users_roles;
DELETE FROM test_inside.user;
DELETE FROM test_inside.role;

insert into test_inside.user (id, name, password) values
('123','Danil', '$2a$12$4t4w.gU.l/p6637WfSiAaOSWs64IfyGM/zmAzbxOI649x8dDjI61C'),
('124','Anton','$2a$12$4t4w.gU.l/p6637WfSiAaOSWs64IfyGM/zmAzbxOI649x8dDjI61C'),
('125','Gena','$2a$12$4t4w.gU.l/p6637WfSiAaOSWs64IfyGM/zmAzbxOI649x8dDjI61C');

insert into test_inside.role (id, name) values
('1','ROLE_USER');

insert into test_inside.users_roles (user_id,role_id) values
('123','1');