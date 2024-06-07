--liquibase formatted sql

--changeset alex:1

ALTER TABLE book
ADD COLUMN description text,
ADD COLUMN language varchar(2)