--liquibase formatted sql

--changeset alex:1
ALTER TABLE book
DROP COLUMN language;

--changeset alex:2
ALTER TABLE book
ADD COLUMN language_id int REFERENCES language(id);