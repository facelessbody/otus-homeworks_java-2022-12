create table address
(
    id        bigserial not null primary key,
    client_id bigint    not null references client (id),
    street    varchar(200)
);

create table phone
(
    id        bigserial not null primary key,
    client_id bigint    not null references client (id),
    number    varchar(10)
);