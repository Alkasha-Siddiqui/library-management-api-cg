package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.model.BookRequestDTO;
import com.capgemini.library.management.project.library_management.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
    public Integer addBook(BookRequestDTO bookRequestDTO) throws DuplicateISBNException {
        Book book = new Book();


        Optional<Book> optionalBook = bookRepository.findByIsbn(bookRequestDTO.getIsbn());

        if (optionalBook.isPresent()) {
            throw new DuplicateISBNException("ISBN already exists in the library"); // Use a specific subclass
        }

        book.setIsbn(bookRequestDTO.getIsbn());
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setPublishYear(bookRequestDTO.getPublishYear());
        book.setGenreIds(bookRequestDTO.getGenreIds());
        book.setAddedDateTime(OffsetDateTime.now(ZoneOffset.UTC));
        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));

        Book bookEntity2 = bookRepository.save(book);
        return bookEntity2.getId();

//        Book book = this.modelMapper.map(bookRequestDTO, Book.class);
//        Book savedBook = bookRepository.save(book);

//        return savedBook.getId();
    }

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
//            bk.setId(book.getId());
//            bk.setTitle(book.getTitle());
//            bk.setAuthor(book.getAuthor());
//            bk.setIsbn(book.getIsbn());
//            bk.setPublishYear(book.getPublishYear());
//            bk.setGenreIds(book.getGenreIds());
//            bk.setAddedDate(OffsetDateTime.now(ZoneOffset.UTC));
//            bk.setUpdatedDate(book.getUpdatedDateTime());
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
//    public BookRequestDTO getAllBooks() {
//        Book book = bookRepository.findAll().get(0);
//        BookRequestDTO bookRequestDTO = BookMapper.mapToBookDto(book, new BookRequestDTO());
//
//        return bookRequestDTO;
//    }

//        @Override
//        public List<Book> getAllBooks() {
//            return bookRepository.findAll();
//        }

//    @Override
//    public BookRequestDTO getAllBooks(Integer page, Integer size) {
//        return null;
//    }


}
