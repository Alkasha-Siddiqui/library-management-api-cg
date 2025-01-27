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

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreAllocationServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenreResponseDTO createGenre(GenreRequestDTO genreRequestDTO) {
        Genre genre = modelMapper.map(genreRequestDTO, Genre.class);
        Genre savedGenre = genreRepository.save(genre);
        return modelMapper.map(savedGenre, GenreResponseDTO.class);
    }

    @Override
    public List<GenreResponseDTO> getAllGenres() {
        List<Genre> allGenres = this.genreRepository.findAll();
        return allGenres.stream().map(genre -> modelMapper.map(genre, GenreResponseDTO.class)).collect(Collectors.toList());
    }

}