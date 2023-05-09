create table client
(
    id   bigserial primary key,
    name varchar(50)
);

create table client_address
(
    id        bigserial not null primary key,
    client_id bigint    not null references client (id),
    street    varchar(200)
);

create table client_phone
(
    id        bigserial not null primary key,
    client_id bigint    not null references client (id),
    number    varchar(10)
);