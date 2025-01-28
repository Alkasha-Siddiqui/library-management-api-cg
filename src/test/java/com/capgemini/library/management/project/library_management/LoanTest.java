package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.library.management.project.library_management.entity.Loan;
import com.capgemini.library.management.project.library_management.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class LoanTest {

    private Loan loan;
    private Member member;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    @BeforeEach
    void setUp() {
        member = new Member(); // Assuming you have a default constructor or appropriate setup
        issueDate = LocalDate.of(2025, 1, 1);
        dueDate = LocalDate.of(2025, 1, 15);
        returnDate = LocalDate.of(2025, 1, 10);

        loan = new Loan();
        loan.setMember(member);
        loan.setIssueDate(issueDate);
        loan.setDueDate(dueDate);
        loan.setReturnDate(returnDate);
    }

    @Test
    void testGetMember() {
        assertEquals(member, loan.getMember());
    }

    @Test
    void testGetIssueDate() {
        assertEquals(issueDate, loan.getIssueDate());
    }

    @Test
    void testGetDueDate() {
        assertEquals(dueDate, loan.getDueDate());
    }

    @Test
    void testGetReturnDate() {
        assertEquals(returnDate, loan.getReturnDate());
    }
}