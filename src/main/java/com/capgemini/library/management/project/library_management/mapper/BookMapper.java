//package com.capgemini.library.management.project.library_management.mapper;
//
//import com.capgemini.library.management.project.library_management.entity.Book;
//import com.capgemini.library.management.project.library_management.model.BookDTO;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//
//@Component
//public class BookMapper {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public Book dtoToBook(BookDTO bookDTO) {
////        Book book = new Book();
////        book.setTitle(bookDTO.getTitle());
////        book.setAuthor(bookDTO.getAuthor());
////        book.setIsbn(bookDTO.getIsbn());
////        book.setPublishYear(bookDTO.getPublishYear());
////        book.setGenreIds(bookDTO.getGenreIds());
////        book.setAddedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
////        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
//
//        Book book = this.modelMapper.map(bookDTO, Book.class);
//        return book;
//    }
//    public BookDTO bookToDto(Book book) {
////        BookDTO bookDTO = new BookDTO();
////        bookDTO.setId(Long.valueOf(book.getId()));
////        bookDTO.setTitle(book.getTitle());
////        bookDTO.setAuthor(book.getAuthor());
////        bookDTO.setIsbn(book.getIsbn());
////        bookDTO.setPublishYear(book.getPublishYear());
////        bookDTO.setGenreIds(book.getGenreIds());
////        bookDTO.setAddedDateTime(book.getAddedDateTime());
////        bookDTO.setUpdatedDateTime(book.getUpdatedDateTime());
//
//        BookDTO bookDTO = this.modelMapper.map(book, BookDTO.class);
//        return bookDTO;
//    }
//}
