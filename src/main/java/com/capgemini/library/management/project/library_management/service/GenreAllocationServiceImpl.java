package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Genre;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "genreService")
@Transactional
public class GenreAllocationServiceImpl implements GenreAllocationService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GenreResponseDTO createGenre(GenreRequestDTO genreRequestDTO) {
        Genre genre = modelMapper.map(genreRequestDTO, Genre.class);
        Genre savedGenre = genreRepository.save(genre);
        GenreResponseDTO genreResponseDTO = modelMapper.map(savedGenre, GenreResponseDTO.class);
        return genreResponseDTO;
    }

    @Override
    public List<GenreResponseDTO> getAllGenres() {
        List<Genre> allGenres = this.genreRepository.findAll();
        List<GenreResponseDTO> genreResponseDTO = allGenres.stream().map(genre -> modelMapper.map(genre, GenreResponseDTO.class)).collect(Collectors.toList());
        return genreResponseDTO;
    }

}