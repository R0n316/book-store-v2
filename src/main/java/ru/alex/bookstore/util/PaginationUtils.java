package ru.alex.bookstore.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static List<Integer> getPageNumbers(int totalPages, int currentPage){
        List<Integer> pageNumbers = new ArrayList<>();
        int startPage = Math.max(0,currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);
        if(endPage - startPage < 4) {
            if(startPage == 0) {
                endPage = Math.min(totalPages - 1, startPage + 4);
            } else {
                startPage = Math.max(0, endPage - 4);
            }
        }
        for(int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }
        return pageNumbers;
    }
}
