package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.model.LoanDTO;
import java.util.List;

public interface LoanAllocationService {
    LoanDTO issueLoan(LoanDTO loanDTO);

    List<LoanDTO> getAllLoans(String status);

    List<LoanDTO> getOverdueLoans();

    LoanDTO returnBook(Long id);
}
