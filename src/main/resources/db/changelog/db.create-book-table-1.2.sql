--liquibase formatted sql

--changeset alex:1

ALTER TABLE book
ADD COLUMN name text NOT NULL