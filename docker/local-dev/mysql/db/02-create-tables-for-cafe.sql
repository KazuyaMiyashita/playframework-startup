use `playframework-startup`;

# drop
drop table if exists `cafes`;
drop table if exists `images`;
drop table if exists `ratings`;

# create
create table if not exists `cafes`
(
  `cafe_id`              bigint unsigned unique not null auto_increment,
  `lattiude`             decimal(9,6) not null,
  `longtitude`           decimal(9,6) not null,
  `name`                 varchar(32) not null,
  primary key (`cafe_id`),
  index (`name`)
);

create table if not exists `images`
(
    `url`           varchar(255) unique not null,
    `cafe_id`       bigint unsigned not null,
    foreign key (`cafe_id`) references `cafes`(`cafe_id`)
);

create table if not exists `ratings`
(
  `cafe_id`           bigint unsigned not null,
  `user_id`           bigint unsigned not null,
  `value`             tinyint not null check (value <= 5 && value >= 1),
  primary key (`user_id`, `cafe_id`),
  foreign key (`user_id`) references `users`(`user_id`),
  foreign key (`cafe_id`) references `cafes`(`cafe_id`)
);
