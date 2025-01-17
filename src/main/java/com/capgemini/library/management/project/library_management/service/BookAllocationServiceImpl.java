package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
//import com.capgemini.library.management.project.library_management.mapper.BookPopulator;
//import com.capgemini.library.management.project.library_management.mapper.BookMapper;
import com.capgemini.library.management.project.library_management.model.BookDTO;
import com.capgemini.library.management.project.library_management.model.PageResponseDTO;
import com.capgemini.library.management.project.library_management.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "bookService")
@Transactional
public class BookAllocationServiceImpl implements BookAllocationService {

    @Autowired
    private BookRepository bookRepository;

//    @Autowired
//    BookMapper bookMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {

        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateISBNException("User", "id", bookDTO.getIsbn());
        }

        Book book = this.dtoToBook(bookDTO);
        Book savedBook = bookRepository.save(book);
        return this.bookToDto(savedBook);
    }

    @Override
    public BookDTO getBookById(Long id)  {
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        return this.bookToDto(book);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublishYear(bookDTO.getPublishYear());
        book.setGenreIds(bookDTO.getGenreIds());
        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));

        Book updatedBook = bookRepository.save(book);
        BookDTO bookDTO1 = this.bookToDto(updatedBook);
        return bookDTO1;
    }

    @Override
    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public PageResponseDTO getAllBooks(Integer page, Integer size) {
        Pageable p = PageRequest.of(page,size);

        Page<Book>  pageBook = this.bookRepository.findAll(p);
        List<Book> allBooks = pageBook.getContent();
        List<BookDTO> bookDTOS = allBooks.stream().map((book) -> this.modelMapper.map(book, BookDTO.class)).collect(Collectors.toList());

        PageResponseDTO pageResponseDTO = new PageResponseDTO();

        pageResponseDTO.setContent(bookDTOS);
        pageResponseDTO.setPageNumber(pageBook.getNumber());
        pageResponseDTO.setPageSize(pageBook.getSize());
        pageResponseDTO.setTotalElements(pageBook.getTotalElements());
        pageResponseDTO.setTotalPages(pageBook.getTotalPages());
        return pageResponseDTO;
    }


    public Book dtoToBook(BookDTO bookDTO) {
//        Book book = new Book();
//        book.setTitle(bookDTO.getTitle());
//        book.setAuthor(bookDTO.getAuthor());
//        book.setIsbn(bookDTO.getIsbn());
//        book.setPublishYear(bookDTO.getPublishYear());
//        book.setGenreIds(bookDTO.getGenreIds());
//        book.setAddedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
//        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));

        Book book = this.modelMapper.map(bookDTO, Book.class);
        return book;
    }

    public BookDTO bookToDto(Book book) {
//        BookDTO bookDTO = new BookDTO();
//        bookDTO.setId(Long.valueOf(book.getId()));
//        bookDTO.setTitle(book.getTitle());
//        bookDTO.setAuthor(book.getAuthor());
//        bookDTO.setIsbn(book.getIsbn());
//        bookDTO.setPublishYear(book.getPublishYear());
//        bookDTO.setGenreIds(book.getGenreIds());
//        bookDTO.setAddedDateTime(book.getAddedDateTime());
//        bookDTO.setUpdatedDateTime(book.getUpdatedDateTime());

        BookDTO bookDTO = this.modelMapper.map(book, BookDTO.class);
        return bookDTO;
    }
}