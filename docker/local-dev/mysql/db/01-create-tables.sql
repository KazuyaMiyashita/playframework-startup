create database if not exists `playframework-startup`;
use `playframework-startup`;

# drop
drop table if exists `auths`;
drop table if exists `roles`;
drop table if exists `tokens`;
drop table if exists `users`;

# create
create table if not exists `roles`
(
  `role_id`         bigint unsigned not null,
  `value`           char(32) not null,
  primary key (`role_id`)
);

create table if not exists `auths`
(
  `auth_id`         bigint unsigned not null auto_increment,
  `email`           char(255) unique not null,
  `hashed_password`        char(60) not null,
  `role_id`         bigint unsigned not null,
  primary key (`auth_id`),
  index (`email`),
  foreign key (`role_id`) references `roles`(`role_id`)
);

create table if not exists `tokens`
(
  `token`           char(255) unique not null,
  `auth_id`         bigint unsigned not null,
  `created_at`      timestamp not null default current_timestamp on update current_timestamp,
  primary key (`token`),
  foreign key (`auth_id`) references `auths`(`auth_id`)
);

create table if not exists `users`
(
  `user_id`          bigint unsigned unique not null auto_increment,
  `auth_id`          bigint unsigned unique not null,
  `name`             varchar(32) not null,
  primary key (`user_id`),
  foreign key (`auth_id`) references `auths`(`auth_id`)
);
