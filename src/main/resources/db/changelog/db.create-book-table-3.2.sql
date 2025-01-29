--liquibase formatted sql

--changeset alex:1
ALTER TABLE book
DROP COLUMN cover_type;

--changeset alex:2
ALTER TABLE book
ADD COLUMN cover_type_id int REFERENCES cover_type(id);