package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.Genre;
import com.capgemini.library.management.project.library_management.exception.AlreadyExistsException;
import com.capgemini.library.management.project.library_management.exception.GenreNotFoundException;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
import com.capgemini.library.management.project.library_management.model.BookRequestDTO;
import com.capgemini.library.management.project.library_management.model.BookResponseDTO;
import com.capgemini.library.management.project.library_management.repository.BookRepository;
import com.capgemini.library.management.project.library_management.repository.GenreRepository;
import com.capgemini.library.management.project.library_management.service.BookAllocationServiceImpl;
import com.capgemini.library.management.project.library_management.repository.BookCopiesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookAllocationServiceImplTest {

    @InjectMocks
    private BookAllocationServiceImpl bookAllocationService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BookCopiesRepository bookCopiesRepository;

    private BookRequestDTO bookRequestDTO;
    private Book book;
    private BookResponseDTO bookResponseDTO;

    @BeforeEach
    void setUp() {
        bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setTitle("Test Book");
        bookRequestDTO.setAuthor("Test Author");
        bookRequestDTO.setIsbn("123-4567890123");
        bookRequestDTO.setGenreIds(Collections.singletonList(1L));

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123-4567890123");

        bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(1L);
        bookResponseDTO.setTitle("Test Book");
        bookResponseDTO.setAuthor("Test Author");
        bookResponseDTO.setIsbn("123-4567890123");
    }

    @Test
     void testAddBook_AlreadyExistsException() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO("Title", "Author", "1234567890123");

        when(bookRepository.existsByIsbn(bookRequestDTO.getIsbn())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> {
            bookAllocationService.addBook(bookRequestDTO);
        });
    }

    @Test
     void testAddBook_GenreNotFoundException() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO("Title", "Author", "1234567890123");
        bookRequestDTO.setGenreIds(List.of(1L, 2L));

        when(bookRepository.existsByIsbn(bookRequestDTO.getIsbn())).thenReturn(false);
        when(genreRepository.findAllById(any())).thenReturn(Collections.singletonList(new Genre()));

        assertThrows(GenreNotFoundException.class, () -> {
            bookAllocationService.addBook(bookRequestDTO);
        });
    }

    @Test
     void testGetBookById_Success() {
        Long bookId = 1L;
        Book book = new Book();
        BookResponseDTO bookResponseDTO = new BookResponseDTO("Title", "Author", "1234567890123");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(modelMapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(bookResponseDTO);

        BookResponseDTO response = bookAllocationService.getBookById(bookId);

        assertEquals(bookResponseDTO, response);
    }

    @Test
     void testGetBookById_ResourceNotFoundException() {
        Long bookId = 1L;

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookAllocationService.getBookById(bookId);
        });
    }

    @Test
     void testUpdateBook_Success() {
        Long bookId = 1L;
        BookRequestDTO bookRequestDTO = new BookRequestDTO("Updated Title", "Updated Author", "1234567890123");
        Book book = new Book();
        Book updatedBook = new Book();
        BookResponseDTO bookResponseDTO = new BookResponseDTO("Updated Title", "Updated Author", "1234567890123");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(modelMapper.map(any(Book.class), eq(BookResponseDTO.class))).thenReturn(bookResponseDTO);

        BookResponseDTO response = bookAllocationService.updateBook(bookId, bookRequestDTO);

        assertEquals(bookResponseDTO, response);
    }

    @Test
     void testUpdateBook_ResourceNotFoundException() {
        Long bookId = 1L;
        BookRequestDTO bookRequestDTO = new BookRequestDTO("Updated Title", "Updated Author", "1234567890123");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookAllocationService.updateBook(bookId, bookRequestDTO);
        });
    }

    @Test
    void testRemoveBook_Success() {
        Long bookId = 1L;

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(bookRepository).deleteById(anyLong());

        bookAllocationService.removeBook(bookId);

        verify(bookRepository).existsById(bookId);

        verify(bookRepository).deleteById(bookId);
    }

    @Test
     void testRemoveBook_ResourceNotFoundException() {
        Long bookId = 1L;

        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            bookAllocationService.removeBook(bookId);
        });
    }
}