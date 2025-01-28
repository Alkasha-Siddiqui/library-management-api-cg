package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.capgemini.library.management.project.library_management.entity.Genre;
import com.capgemini.library.management.project.library_management.model.GenreRequestDTO;
import com.capgemini.library.management.project.library_management.model.GenreResponseDTO;
import com.capgemini.library.management.project.library_management.repository.GenreRepository;
import com.capgemini.library.management.project.library_management.service.GenreAllocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GenreAllocationServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GenreAllocationServiceImpl genreAllocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGenre() {
        GenreRequestDTO genreRequestDTO = new GenreRequestDTO();
        genreRequestDTO.setName("Fiction");

        Genre genre = new Genre();
        genre.setName("Fiction");

        Genre savedGenre = new Genre();
        savedGenre.setId(1L);
        savedGenre.setName("Fiction");

        GenreResponseDTO genreResponseDTO = new GenreResponseDTO();
        genreResponseDTO.setId(1L);
        genreResponseDTO.setName("Fiction");

        when(modelMapper.map(any(GenreRequestDTO.class), any(Class.class))).thenReturn(genre);
        when(genreRepository.save(any(Genre.class))).thenReturn(savedGenre);
        when(modelMapper.map(any(Genre.class), any(Class.class))).thenReturn(genreResponseDTO);

        GenreResponseDTO result = genreAllocationService.createGenre(genreRequestDTO);

        assertEquals(1L, result.getId());
        assertEquals("Fiction", result.getName());
    }

    @Test
    void testGetAllGenres() {
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Non-Fiction");

        List<Genre> allGenres = Stream.of(genre1, genre2).collect(Collectors.toList());

        GenreResponseDTO genreResponseDTO1 = new GenreResponseDTO();
        genreResponseDTO1.setId(1L);
        genreResponseDTO1.setName("Fiction");

        GenreResponseDTO genreResponseDTO2 = new GenreResponseDTO();
        genreResponseDTO2.setId(2L);
        genreResponseDTO2.setName("Non-Fiction");

        when(genreRepository.findAll()).thenReturn(allGenres);
        when(modelMapper.map(genre1, GenreResponseDTO.class)).thenReturn(genreResponseDTO1);
        when(modelMapper.map(genre2, GenreResponseDTO.class)).thenReturn(genreResponseDTO2);

        List<GenreResponseDTO> result = genreAllocationService.getAllGenres();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Fiction", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Non-Fiction", result.get(1).getName());
    }
}