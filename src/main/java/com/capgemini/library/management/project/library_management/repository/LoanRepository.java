package com.capgemini.library.management.project.library_management.repository;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Optional<Loan> findById(Long id);
    List<Loan> findByStatus(Loan.StatusEnum status);
}
