create database if not exists `playframework-startup`;
use `playframework-startup`;

# drop
drop table if exists `auths`;
drop table if exists `tokens`;
drop table if exists `users`;

# create
create table if not exists `auths`
(
  `id`              char(64) unique not null,
  `email`           char(255) unique not null,
  `password`        char(60) not null,
  primary key (`id`),
  index (`email`)
);

create table if not exists `tokens`
(
  `token`           char(255) unique not null,
  `auth_id`         char(255) not null,
  `created_at`      timestamp not null,
  primary key (`token`),
  foreign key (`auth_id`) references `auths`(`id`)
);


create table if not exists `users`
(
  `id`               char(64) unique not null,
  `auth_id`          char(64) unique not null,
  `name`             VARCHAR(32) NOT NULL,
  primary key (`id`),
  foreign key (`auth_id`) references `auths`(`id`)
);
