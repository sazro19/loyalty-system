drop table if exists customers;
drop table if exists coupons;

create table customers
(
    id    BIGINT AUTO_INCREMENT primary key,
    email VARCHAR(1000),
    bonus INTEGER,
    UNIQUE (email)
);

create table coupons
(
    id   BIGINT AUTO_INCREMENT primary key,
    code VARCHAR(1000),
    off  FLOAT,
    UNIQUE (code)
);