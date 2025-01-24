package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Loan;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
import com.capgemini.library.management.project.library_management.exception.LoanNotIssuedException;
import com.capgemini.library.management.project.library_management.model.LoanDTO;
import com.capgemini.library.management.project.library_management.repository.LoanRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "loanService")
@Transactional
public class LoanAllocationServiceImpl implements LoanAllocationService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public LoanDTO issueLoan(LoanDTO loanDTO) {
        Loan loan = modelMapper.map(loanDTO, Loan.class);
        Loan savedLoan = loanRepository.save(loan);
        LoanDTO loanResponseDTO = modelMapper.map(savedLoan, LoanDTO.class);
        return loanResponseDTO;
    }

    @Override
    public List<LoanDTO> getAllLoans(String status) {
        List<Loan> loans;
        if (status == null) {
            loans = loanRepository.findAll();
        } else {
            loans = loanRepository.findByStatus(Loan.StatusEnum.valueOf(status));
        }
        return loans.stream().map(loan -> modelMapper.map(loan, LoanDTO.class)).collect(Collectors.toList());
    }

    @Override
    public LoanDTO returnBook(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan", "id", id));

        if (!loan.getStatus().equals(Loan.StatusEnum.ISSUED)) {
            throw new LoanNotIssuedException("Loan", "id", loan.getId());
        }

        loan.setStatus(Loan.StatusEnum.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loan = loanRepository.save(loan);

        return modelMapper.map(loan, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> getOverdueLoans() {
        List<Loan> overdueLoans = loanRepository.findByStatus(Loan.StatusEnum.OVERDUE);
        return overdueLoans.stream()
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());
    }
}