create table if not exists products(
    id serial primary key,
    name varchar(255) not null ,
    model_name varchar(255) not null,
    abbreviature varchar(255) not null
);
create table if not exists warranties(
    id serial primary key,
    product_id int references products(id) not null,
    date timestamp not null,
    serial_number varchar(255) not null
);
insert into products(name, model_name, abbreviature) VALUES
('Dune HD Magic 4K PLUS', 'TV-175R', '175R'),
('Dune HD Magic 4K', 'TV-175Q', '175Q'),
('Dune HD Smart Box 4K', 'TV-175L', '175L'),
('Dune HD Smart Box 4K PlUS', 'TV-175N', '175N');
