package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.exception.LibraryManagementException;
import com.capgemini.library.management.project.library_management.mapper.BookPopulator;
import com.capgemini.library.management.project.library_management.model.BookDTO;
import com.capgemini.library.management.project.library_management.model.BookDTO;
import com.capgemini.library.management.project.library_management.model.PageResponseDTO;
import com.capgemini.library.management.project.library_management.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "bookService")
@Transactional
public class BookAllocationServiceImpl implements BookAllocationService {

    @Autowired
    private BookRepository bookRepository;

//    @Autowired
//    private BookMapper bookMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Integer addBook(BookDTO bookDTO) throws DuplicateISBNException {
        Optional<Book> optionalBook = bookRepository.findByIsbn(bookDTO.getIsbn());

        if (optionalBook.isPresent()) {
            throw new DuplicateISBNException("ISBN already exists in the library"); // Use a specific subclass
        }

        Book book = BookPopulator.INSTANCE.populateBook(bookDTO);
        book.setAddedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
        return bookRepository.save(book).getId();
    }

//    @Override
//    public List<Book> getAllBooks(int page, int size) {
//        List<Book> listOfBooks = bookRepository.findAll(PageResponseDTO.of(page, size));
//        return listOfBooks;
//    }

//    public List<Book> getAllBooks(int page, int size) {
//        PageRequest pageRequest = PageResponseDTO.of(page, size); // Use static method
//        List<Book> listOfBooks = bookRepository.findAll(pageRequest).getContent();
//        return listOfBooks;
//    }

    @Override
    public BookDTO getBookById(Integer id) throws DuplicateISBNException{
        Optional<Book> optionalBook = bookRepository.findByIsbn(bookDTO.getIsbn());

        if (optionalBook.isPresent()) {
            throw new DuplicateISBNException("ISBN already exists in the library"); // Use a specific subclass
        }BookDTO book2 = new BookDTO();

        book2.setTitle(book.getTitle());
        book2.setAuthor(book.getAuthor());
        book2.setIsbn(book.getIsbn());
        book2.setPublishYear(book.getPublishYear());
        book2.setGenreIds(book.getGenreIds());
//        book2.setAddedDate(book.getAddedDate());
//        book2.setUpdatedDate(book.getUpdatedDate());

        return book2;
    }
    }
//    public List<Book> getAllBooks(){
//        return bookRepository.findAll();
//    }

//    public ResponseEntity<List<Book>> getAllBooks(){
//        List<Book> listOfBooks = new ArrayList<>();
//        listOfBooks.addAll(BookAllocationService.getAllBooks());
//
//        return new ResponseEntity<>(listOfBooks, HttpStatus.OK);
//    }
//    @Override
//    public Integer addBook(BookDTO bookDTO) throws DuplicateISBNException {
//        Book book = new Book();
//
//
//        Optional<Book> optionalBook = bookRepository.findByIsbn(bookDTO.getIsbn());
//
//        if (optionalBook.isPresent()) {
//            throw new DuplicateISBNException("ISBN already exists in the library"); // Use a specific subclass
//        }
//
//        book.setIsbn(bookDTO.getIsbn());
//        book.setTitle(bookDTO.getTitle());
//        book.setAuthor(bookDTO.getAuthor());
//        book.setPublishYear(bookDTO.getPublishYear());
//        book.setGenreIds(bookDTO.getGenreIds());
//        book.setAddedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
//        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
//
//        Book bookEntity2 = bookRepository.save(book);
//        return bookEntity2.getId();
//
//        Book book = this.modelMapper.map(bookDTO, Book.class);
//        Book savedBook = bookRepository.save(book);
//
//        return savedBook.getId();
//    }

//    @Override
//    public List<BookResponseDTO> getAllBooks() {
//        return List.of();
//    }

//    @Override
//    public List<BookResponseDTO> getAllBooks() {
//        List<Book> bookList = bookRepository.findAll();
//        return bookList.stream()
//                .map(book -> BookMapper.mapToBookResponseDTO(book, new BookResponseDTO()))
//                .collect(Collectors.toList());
//    }

//    @Override
//    public List<BookResponseDTO> getAllBooks() {
//        Iterable<Book> books = bookRepository.findAll();
//        List<BookResponseDTO> bookDTOs = new ArrayList<>();
//        books.forEach(book -> {
//            BookResponseDTO bk = new BookResponseDTO();
//
//            bk.setId(Long.valueOf(book.getId()));
//            bk.setTitle(book.getTitle());
//            bk.setAuthor(book.getAuthor());
//            bk.setIsbn(book.getIsbn());
//            bk.setPublishYear(book.getPublishYear());
//            bk.setGenreIds(book.getGenreIds());
////            bk.setAddedDate(OffsetDateTime.now(ZoneOffset.UTC));
////            bk.setUpdatedDate(book.getUpdatedDateTime());
//
//            bookDTOs.add(bk);
//        });
//        if (bookDTOs.isEmpty())
//            try {
//                throw new LibraryManagementException("Service.PROJECTS_NOT_FOUND");
//            } catch (LibraryManagementException e) {
//                throw new RuntimeException(e);
//            }
//        return bookDTOs;
//    }

//    @Override
//    public BookDTO getAllBooks() {
//        Book book = bookRepository.findAll().get(0);
//        BookDTO bookDTO = BookMapper.mapToBookDto(book, new BookDTO());
//
//        return bookDTO;
//    }

//        @Override
//        public List<Book> getAllBooks() {
//            return bookRepository.findAll();
//        }

//    @Override
//    public BookDTO getAllBooks(Integer page, Integer size) {
//        return null;
//    }


}
