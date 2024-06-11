--liquibase formatted sql

--changeset alex:1
ALTER TABLE book
DROP COLUMN is_in_favorites;

--changeset alex:2

ALTER TABLE book
DROP COLUMN is_in_cart