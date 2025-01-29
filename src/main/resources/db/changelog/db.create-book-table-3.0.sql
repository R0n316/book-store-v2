--liquibase formatted sql

--changeset alex:1
ALTER TABLE book
DROP COLUMN publisher;

--changeset alex:2
ALTER TABLE book
ADD COLUMN publisher_id int REFERENCES publisher(id);