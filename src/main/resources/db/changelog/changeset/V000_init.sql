CREATE SEQUENCE unique_hash_number_seq
    START WITH 1
    INCREMENT BY 1;

create table if not exists url
(
    hashId       varchar(8) primary key,
    url          text not null,
    created_at   timestamp default CURRENT_TIMESTAMP,
    updated_at   timestamp default CURRENT_TIMESTAMP,
    requested_at timestamp
);

create table if not exists hash
(
    hash varchar(8) primary key
);