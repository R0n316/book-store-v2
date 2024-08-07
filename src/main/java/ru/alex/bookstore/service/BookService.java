package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.alex.bookstore.dto.BookCreateEditDto;
import ru.alex.bookstore.dto.BookFilter;
import ru.alex.bookstore.dto.BookPreviewDto;
import ru.alex.bookstore.dto.BookReadDto;
import ru.alex.bookstore.mapper.BookCreateEditMapper;
import ru.alex.bookstore.mapper.BookPreviewMapper;
import ru.alex.bookstore.database.repository.BookRepository;
import ru.alex.bookstore.mapper.BookReadMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final BookPreviewMapper bookPreviewMapper;
    private final BookCreateEditMapper bookCreateEditMapper;
    private final BookReadMapper bookReadMapper;
    private final ImageService imageService;

    @Autowired
    public BookService(BookRepository bookRepository,
                       BookPreviewMapper bookPreviewMapper,
                       BookCreateEditMapper bookCreateEditMapper,
                       BookReadMapper bookReadMapper,
                       ImageService imageService) {
        this.bookRepository = bookRepository;
        this.bookPreviewMapper = bookPreviewMapper;
        this.bookCreateEditMapper = bookCreateEditMapper;
        this.bookReadMapper = bookReadMapper;
        this.imageService = imageService;
    }

    public Page<BookPreviewDto> findAllByFilter(BookFilter filter, Pageable pageable){
        return bookRepository.findAllByFilter(filter,pageable)
                .map(bookPreviewMapper::map);
    }

    @Cacheable("images")
    public Optional<byte[]> findImage(Integer id){
        return bookRepository.getImagePathById(id)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public BookPreviewDto create(BookCreateEditDto bookDto){
        return Optional.of(bookDto)
                .map(dto -> {
                    uploadImage(dto.image());
                    return bookCreateEditMapper.map(dto);
                })
                .map(bookRepository::save)
                .map(bookPreviewMapper::map)
                .orElseThrow(() -> new RuntimeException("Failed to create book"));
    }

    private void uploadImage(MultipartFile image) {
        if(!image.isEmpty()){
            try {
                imageService.upload(image.getOriginalFilename(),image.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Optional<BookReadDto> findById(Integer id){
        return bookRepository.findById(id)
                .map(bookReadMapper::map);
    }

    public List<BookPreviewDto> findTopByRating(Integer limit){
        return bookRepository.findTopByRating(limit)
                .stream()
                .map(bookPreviewMapper::map)
                .toList();
    }

    public List<BookPreviewDto> findTopByCirculation(Integer limit){
        return bookRepository.findTopByCirculation(limit)
                .stream()
                .map(bookPreviewMapper::map)
                .toList();
    }

}
