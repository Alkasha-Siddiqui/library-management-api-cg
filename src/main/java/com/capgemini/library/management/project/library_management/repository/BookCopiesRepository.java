package com.capgemini.library.management.project.library_management.repository;

import com.capgemini.library.management.project.library_management.entity.BookCopies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCopiesRepository extends JpaRepository<BookCopies, Integer> {
    Optional<BookCopies> findByBookId(Long bookId);
}