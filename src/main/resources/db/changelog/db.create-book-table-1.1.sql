--liquibase formatted sql

--changeset alex:1

ALTER TABLE book
ADD COLUMN publisher varchar(64),
ADD COLUMN series VARCHAR(32),
ADD column year_of_publishing int,
ADD COLUMN isbn varchar(17),
ADD COLUMN  number_of_pages int,
ADD COLUMN size varchar(32),
ADD COLUMN cover_type varchar(16),
ADD COLUMN circulation bigint,
ADD COLUMN weight int,
ADD age_restrictions int