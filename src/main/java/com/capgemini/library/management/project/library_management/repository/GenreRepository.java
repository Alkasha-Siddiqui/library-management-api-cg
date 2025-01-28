package com.capgemini.library.management.project.library_management.repository;

import com.capgemini.library.management.project.library_management.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}