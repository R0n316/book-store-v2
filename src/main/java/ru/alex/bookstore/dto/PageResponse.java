package ru.alex.bookstore.dto;

import org.springframework.data.domain.Slice;

import java.util.List;

public record PageResponse<T> (
        List<T> content,
        Metadata metadata
) {
    public static <T> PageResponse<T> of(Slice<T> slice){
        Metadata metadata = new Metadata(slice.getNumber(), slice.getSize());
        return new PageResponse<>(slice.getContent(), metadata);
    }

    public record Metadata(
            int page,
            int size
    ){

    }
}
