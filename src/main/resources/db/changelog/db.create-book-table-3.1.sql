--liquibase formatted sql

--changeset alex:1
ALTER TABLE book
DROP COLUMN author;

--changeset alex:2
ALTER TABLE book
ADD COLUMN author_id int REFERENCES author(id);