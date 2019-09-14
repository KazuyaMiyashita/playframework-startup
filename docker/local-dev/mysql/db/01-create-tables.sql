create database if not exists `playframework-startup`;
use `playframework-startup`;

# drop
drop table if exists `auths`;
drop table if exists `tokens`;
drop table if exists `users`;

# create
create table if not exists `auths`
(
  `auth_id`         bigint unsigned not null auto_increment,
  `email`           char(255) unique not null,
  `password`        char(60) not null,
  primary key (`auth_id`),
  index (`email`)
);

create table if not exists `tokens`
(
  `token`           char(255) unique not null,
  `auth_id`         bigint unsigned not null,
  `created_at`      timestamp not null,
  primary key (`token`),
  foreign key (`auth_id`) references `auths`(`auth_id`)
);

create table if not exists `users`
(
  `user_id`          bigint unsigned unique not null auto_increment,
  `auth_id`          bigint unsigned not null,
  `name`             varchar(32) not null,
  primary key (`user_id`),
  foreign key (`auth_id`) references `auths`(`auth_id`)
);
