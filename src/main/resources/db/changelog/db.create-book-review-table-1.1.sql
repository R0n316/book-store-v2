--liquibase formatted sql

--changeset alex:1
ALTER TABLE book_review
DROP COLUMN likes;

--changeset alex:2
ALTER TABLE book_review
DROP COLUMN dislikes