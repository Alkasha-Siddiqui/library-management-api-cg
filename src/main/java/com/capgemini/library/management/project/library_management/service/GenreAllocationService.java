package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.model.*;
import java.util.List;

public interface GenreAllocationService {

    GenreResponseDTO createGenre(GenreRequestDTO genreRequestDTO);

    List<GenreResponseDTO> getAllGenres();
}
