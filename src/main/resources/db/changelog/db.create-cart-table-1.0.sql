--liquibase formatted sql

--changeset alex:1
CREATE TABLE cart
(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    user_id int REFERENCES users(id) NOT NULL,
    book_id int REFERENCES book(id) NOT NULL,
    quantity int NOT NULL
)