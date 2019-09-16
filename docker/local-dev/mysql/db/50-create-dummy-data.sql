use `playframework-startup`;

insert into `roles` (role_id, value) values (1, "user");
insert into `roles` (role_id, value) values (2, "admin");
insert into `cafes` (latitude, longitude, name) values (35.738044, 139.757419, "コメダ珈琲店 田端駅前店");
insert into `cafes` (latitude, longitude, name) values (35.743503, 139.800003, "BUoY Cafe");
insert into `auths` (email, hashed_password, role_id) values ("a@example.com", "scala", 1);
insert into `auths` (email, hashed_password, role_id) values ("b@example.com", "java", 2);
insert into `users` (auth_id, name) values (1, "asai");
insert into `users` (auth_id, name) values (2, "kijima");
insert into `images` (url, cafe_id) values ("http://example.com/coffee", 1);
insert into `images` (url, cafe_id) values ("http://example.com/tea", 1);
insert into `ratings` (cafe_id, user_id, value) values (1, 1, 5);
insert into `ratings` (cafe_id, user_id, value) values (1, 2, 4);
