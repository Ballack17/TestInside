DELETE FROM test_inside.users_roles;
DELETE FROM test_inside.user;
DELETE FROM test_inside.role;

INSERT INTO test_inside.user (id, name, password) VALUES
('123','Danil', '$2a$12$4t4w.gU.l/p6637WfSiAaOSWs64IfyGM/zmAzbxOI649x8dDjI61C'),
('124','Anton','$2a$12$4t4w.gU.l/p6637WfSiAaOSWs64IfyGM/zmAzbxOI649x8dDjI61C'),
('125','Gena','$2a$12$4t4w.gU.l/p6637WfSiAaOSWs64IfyGM/zmAzbxOI649x8dDjI61C');

INSERT INTO test_inside.role (id, name) VALUES
('1','ROLE_USER');

INSERT INTO test_inside.users_roles (user_id,role_id) VALUES
('123','1');

INSERT INTO test_inside.message (id, message, user_id, created_at) VALUES
('1001','message1','123','2022--4-27 07:34:38'),
('1002','message2','123','2022--4-27 07:34:39'),
('1003','message3','123','2022--4-27 07:34:40'),
('1004','message4','123','2022--4-27 07:34:41'),
('1005','message5','123','2022--4-27 07:34:42'),
('1006','message6','123','2022--4-27 07:34:43'),
('1007','message7','123','2022--4-27 07:34:44'),
('1008','message8','123','2022--4-27 07:34:45'),
('1009','message9','123','2022--4-27 07:34:46'),
('1010','message10','123','2022--4-27 07:34:47'),
('1011','message11','123','2022--4-27 07:34:48'),
('1012','message12','123','2022--4-27 07:34:49');