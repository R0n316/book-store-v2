--liquibase formatted sql

--changeset alex:1

ALTER TABLE book
ALTER COLUMN rating
TYPE float