package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.entity.Loan;
import com.capgemini.library.management.project.library_management.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private Member member;
    private List<Loan> loans;

    @BeforeEach
    void setUp() {
        member = new Member();
        loans = new ArrayList<>();
        loans.add(new Loan());
    }

    @Test
    void testGetId() {
        member.setId(1L);
        assertEquals(1L, member.getId());
    }

    @Test
    void testGetFirstName() {
        member.setFirstName("John");
        assertEquals("John", member.getFirstName());
    }

    @Test
    void testGetLastName() {
        member.setLastName("Doe");
        assertEquals("Doe", member.getLastName());
    }

    @Test
    void testGetEmail() {
        member.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", member.getEmail());
    }

    @Test
    void testGetMembershipDate() {
        LocalDate membershipDate = LocalDate.of(2023, 1, 1);
        member.setMembershipDate(membershipDate);
        assertEquals(membershipDate, member.getMembershipDate());
    }

    @Test
    void testGetUpdatedDateTime() {
        OffsetDateTime updatedDateTime = OffsetDateTime.now();
        member.setUpdatedDateTime(updatedDateTime);
        assertEquals(updatedDateTime, member.getUpdatedDateTime());
    }

    @Test
    void testSetUpdatedDateTime() {
        OffsetDateTime updatedDateTime = OffsetDateTime.now();
        member.setUpdatedDateTime(updatedDateTime);
        assertEquals(updatedDateTime, member.getUpdatedDateTime());
    }

    @Test
    void testGetLoans() {
        member.setLoans(loans);
        assertEquals(loans, member.getLoans());
    }

    @Test
    void testSetLoans() {
        member.setLoans(loans);
        assertEquals(loans, member.getLoans());
    }
}