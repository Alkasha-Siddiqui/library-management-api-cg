package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.BookCopies;
import com.capgemini.library.management.project.library_management.entity.Genre;
import com.capgemini.library.management.project.library_management.exception.AlreadyExistsException;
import com.capgemini.library.management.project.library_management.exception.GenreNotFoundException;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.repository.BookCopiesRepository;
import com.capgemini.library.management.project.library_management.repository.BookRepository;
import com.capgemini.library.management.project.library_management.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "bookService")
@Transactional
public class BookAllocationServiceImpl implements BookAllocationService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final BookCopiesRepository bookCopiesRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookAllocationServiceImpl(BookRepository bookRepository, GenreRepository genreRepository,
                       BookCopiesRepository bookCopiesRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.bookCopiesRepository = bookCopiesRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO) {

        if (bookRepository.existsByIsbn(bookRequestDTO.getIsbn())) {
            throw new AlreadyExistsException("ISBN", "id", bookRequestDTO.getIsbn());
        }

        List<Long> genreIds = bookRequestDTO.getGenreIds();
        List<Genre> genres = genreRepository.findAllById(genreIds);
        if (genres.size() != genreIds.size()) {
            List<Long> foundGenreIds = genres.stream().map(Genre::getId).collect(Collectors.toList());
            List<Long> notFoundGenreIds = genreIds.stream().filter(id -> !foundGenreIds.contains(id)).collect(Collectors.toList());
            throw new GenreNotFoundException(notFoundGenreIds);
        }

        Book book = modelMapper.map(bookRequestDTO, Book.class);
        book.setGenres(genres); // Set the genres to the book
        Book savedBook = bookRepository.save(book);

        BookCopies bookCopies = new BookCopies();
        bookCopies.setBook(savedBook);
        bookCopies.setTotalCopies(3);
        bookCopies.setAvailableCopies(3);
        bookCopiesRepository.save(bookCopies);

        return modelMapper.map(savedBook, BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO getBookById(Long id)  {
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book", "id", id));
        return modelMapper.map(book, BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book", "id", id));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublishYear(bookDTO.getPublishYear());
        book.setGenreIds(bookDTO.getGenreIds());
        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));

        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookResponseDTO.class);
    }

    @Override
    public void removeBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public PageResponseDTO getAllBooks(Integer page, Integer size) {
        Pageable p = PageRequest.of(page,size);

        Page<Book>  pageBook = this.bookRepository.findAll(p);
        List<Book> allBooks = pageBook.getContent();
        List<BookResponseDTO> bookDTOS = allBooks.stream().map(book -> this.modelMapper.map(book, BookResponseDTO.class)).collect(Collectors.toList());

        PageResponseDTO pageResponseDTO = new PageResponseDTO();

        pageResponseDTO.setContent(bookDTOS);
        pageResponseDTO.setPageNumber(pageBook.getNumber());
        pageResponseDTO.setPageSize(pageBook.getSize());
        pageResponseDTO.setTotalElements(pageBook.getTotalElements());
        pageResponseDTO.setTotalPages(pageBook.getTotalPages());
        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO searchBooks(BookSearchRequestDTO bookSearchRequestDTO) {
        Sort.Direction direction = Sort.Direction.ASC; // Default to ASC
        if (bookSearchRequestDTO.getSortDirection() != null) {
            direction = Sort.Direction.valueOf(bookSearchRequestDTO.getSortDirection().name().toUpperCase());
        }

        PageRequest pageRequest = PageRequest.of(
                bookSearchRequestDTO.getPage(),
                bookSearchRequestDTO.getSize(),
                Sort.by(direction, bookSearchRequestDTO.getSortBy().getValue())
        );

        Page<Book> bookPage = bookRepository.findAll(pageRequest);

        List<BookResponseDTO> bookResponseDTOs = bookPage.getContent().stream()
                .filter(book -> filterBook(book, bookSearchRequestDTO))
                .map(this::convertToBookResponseDTO)
                .collect(Collectors.toList());

        PageResponseDTO pageResponseDTO = new PageResponseDTO();
        pageResponseDTO.setContent(bookResponseDTOs);
        pageResponseDTO.setTotalElements((long) bookResponseDTOs.size());
        pageResponseDTO.setTotalPages((int) Math.ceil((double) bookResponseDTOs.size() / bookSearchRequestDTO.getSize()));
        pageResponseDTO.setPageNumber(bookSearchRequestDTO.getPage());
        pageResponseDTO.setPageSize(bookSearchRequestDTO.getSize());

        return pageResponseDTO;
    }

    private boolean filterBook(Book book, BookSearchRequestDTO request) {
        if (request.getSearchTerm() != null && !book.getTitle().contains(request.getSearchTerm()) && !book.getAuthor().contains(request.getSearchTerm())) {
            return false;
        }
        if (request.getGenreId() != null && !book.getGenreIds().contains(request.getGenreId())) {
            return false;
        }
        if (request.getAuthor() != null && !book.getAuthor().contains(request.getAuthor())) {
            return false;
        }
        if (request.getPublishYearFrom() != null && book.getPublishYear() < request.getPublishYearFrom()) {
            return false;
        }
        if (request.getPublishYearTo() != null && book.getPublishYear() > request.getPublishYearTo()) {
            return false;
        }

        return true;
    }

    private BookResponseDTO convertToBookResponseDTO(Book book) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(book.getId());
        bookResponseDTO.setTitle(book.getTitle());
        bookResponseDTO.setAuthor(book.getAuthor());
        bookResponseDTO.setIsbn(book.getIsbn());
        bookResponseDTO.setPublishYear(book.getPublishYear());
        bookResponseDTO.setGenreIds(book.getGenreIds());
        bookResponseDTO.setAddedDateTime(book.getAddedDateTime());
        bookResponseDTO.setUpdatedDateTime(book.getUpdatedDateTime());
        return bookResponseDTO;
    }
}