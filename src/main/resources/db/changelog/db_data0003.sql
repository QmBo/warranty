create table if not exists users(
    id serial primary key,
    name varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    is_active boolean not null,
    role varchar(255) not null
);

insert into users(name, username, password, is_active, role) values
('Администратор', 'admin', '$2a$08$xDJ4lsYKSRLNmI6txqK2a.08NraFv2326pW/1pILqGFMvrzsnqnXu', true, 'ADMIN'),
('Модератор', 'moder', '$2a$08$q5k05Qx5kx0wc07SHgMxYeu1CMNkr65Fp.qhc50zavkmPi5e4.Cxu', true, 'MODER');