--liquibase formatted sql

--changeset alex:1
CREATE TABLE author
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name text NOT NULL
)