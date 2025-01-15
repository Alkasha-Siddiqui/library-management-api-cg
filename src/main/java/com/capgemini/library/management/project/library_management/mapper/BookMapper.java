package com.capgemini.library.management.project.library_management.mapper;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.model.BookRequestDTO;
import com.capgemini.library.management.project.library_management.model.BookResponseDTO;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
@Configuration
public interface BookMapper {


    @Bean
    public static BookRequestDTO mapToBookRequestDto(Book book, BookRequestDTO bookRequestDto) {
        bookRequestDto.setTitle(book.getTitle());
        bookRequestDto.setAuthor(book.getAuthor());
        bookRequestDto.setIsbn(book.getIsbn());
        bookRequestDto.setPublishYear(book.getPublishYear());
        bookRequestDto.setGenreIds(book.getGenreIds());

        return bookRequestDto;
    }

    @Bean
    public static Book mapToBook(BookRequestDTO bookRequestDto, Book book) {
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublishYear(bookRequestDto.getPublishYear());
        book.setGenreIds(bookRequestDto.getGenreIds());
        book.setAddedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));

        return book;
    }


    @Bean
    public static BookResponseDTO mapToBookResponseDTO(Book book, BookResponseDTO bookResponseDto) {
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setAuthor(book.getAuthor());
        bookResponseDto.setIsbn(book.getIsbn());
        bookResponseDto.setPublishYear(book.getPublishYear());
        bookResponseDto.setGenreIds(book.getGenreIds());
        bookResponseDto.setAddedDate(book.getAddedDateTime());
        bookResponseDto.setUpdatedDate(book.getUpdatedDateTime());

        return bookResponseDto;
    };

}