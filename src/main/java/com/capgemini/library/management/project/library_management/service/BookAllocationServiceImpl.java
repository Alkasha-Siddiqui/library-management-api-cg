package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
import com.capgemini.library.management.project.library_management.model.BookRequestDTO;
import com.capgemini.library.management.project.library_management.model.BookResponseDTO;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO) {

        if (bookRepository.existsByIsbn(bookRequestDTO.getIsbn())) {
            throw new DuplicateISBNException("User", "id", bookRequestDTO.getIsbn());
        }

        Book book = modelMapper.map(bookRequestDTO, Book.class);
        Book savedBook = bookRepository.save(book);
        BookResponseDTO bookResponseDTO = modelMapper.map(savedBook, BookResponseDTO.class);
        return bookResponseDTO;
    }

    @Override
    public BookResponseDTO getBookById(Long id)  {
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        BookResponseDTO bookResponseDTO = modelMapper.map(book, BookResponseDTO.class);
        return bookResponseDTO;
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublishYear(bookDTO.getPublishYear());
        book.setGenreIds(bookDTO.getGenreIds());
        book.setUpdatedDateTime(OffsetDateTime.now(ZoneOffset.UTC));

        Book updatedBook = bookRepository.save(book);
        BookResponseDTO bookResponseDTO = modelMapper.map(updatedBook, BookResponseDTO.class);
        return bookResponseDTO;
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
        List<BookResponseDTO> bookDTOS = allBooks.stream().map((book) -> this.modelMapper.map(book, BookResponseDTO.class)).collect(Collectors.toList());

        PageResponseDTO pageResponseDTO = new PageResponseDTO();

        pageResponseDTO.setContent(bookDTOS);
        pageResponseDTO.setPageNumber(pageBook.getNumber());
        pageResponseDTO.setPageSize(pageBook.getSize());
        pageResponseDTO.setTotalElements(pageBook.getTotalElements());
        pageResponseDTO.setTotalPages(pageBook.getTotalPages());
        return pageResponseDTO;
    }

}