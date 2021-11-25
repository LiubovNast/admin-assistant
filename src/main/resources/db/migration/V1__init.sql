create table roles
(
    id          int  not null primary key,
    description text not null
);

insert into roles (id, description)
values (0, 'ROLE_OWNER');
insert into roles (id, description)
values (1, 'ROLE_ADMIN');
insert into roles (id, description)
values (2, 'ROLE_WORKER');
insert into roles (id, description)
values (3, 'ROLE_USER');
insert into roles (id, description)
values (4, 'ROLE_BAN');

CREATE TABLE admins
(
    id       serial primary key,
    name     text not null,
    login    text not null unique,
    password text not null,
    role     int  not null references roles (id)
);

CREATE TABLE users
(
    id    serial primary key,
    name  text not null,
    phone text not null unique,
    role  int  not null references roles (id)
);

CREATE TABLE specialists
(
    id       serial primary key,
    name     text not null,
    login    text not null unique,
    password text not null,
    role     int  not null references roles (id)
);

CREATE TABLE procedures
(
    id            serial primary key,
    name          text   not null,
    specialist_id bigint references specialists (id) on delete cascade,
    price         bigint not null,
    duration      bigint not null
);

CREATE TABLE records
(
    id            serial primary key,
    procedure_id  bigint references procedures (id),
    time_when     timestamp not null,
    user_id       bigint references users (id) on delete cascade
);

CREATE TABLE free_time
(
    id            serial primary key,
    specialist_id bigint references specialists (id) on delete cascade,
    time_from     timestamp not null,
    time_to       timestamp not null
);