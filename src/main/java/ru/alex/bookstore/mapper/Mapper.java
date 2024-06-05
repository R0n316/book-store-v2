package ru.alex.bookstore.mapper;

public interface Mapper<F,T> {
    T map(F object);
}
