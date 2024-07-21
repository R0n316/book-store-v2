--liquibase formatted sql

--changeset alex:1

ALTER TABLE book_review
ADD COLUMN created_at timestamp;

--changeset alex:2

ALTER TABLE book_review
ADD COLUMN updated_at timestamp