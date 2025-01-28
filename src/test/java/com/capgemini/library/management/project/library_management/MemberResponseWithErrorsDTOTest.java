package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.library.management.project.library_management.model.ErrorDTO;
import com.capgemini.library.management.project.library_management.model.MemberResponseWithErrorsDTO;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class MemberResponseWithErrorsDTOTest {

    @Test
    void testMemberResponseWithErrorsDTO() {
        MemberResponseWithErrorsDTO memberResponse1 = new MemberResponseWithErrorsDTO();
        MemberResponseWithErrorsDTO memberResponse2 = new MemberResponseWithErrorsDTO();

        memberResponse1.setId(1L);
        memberResponse1.setFirstName("John");
        memberResponse1.setLastName("Doe");
        memberResponse1.setEmail("john.doe@example.com");

        ErrorDTO error1 = new ErrorDTO();
        error1.setCode("MEMBER_NOT_FOUND");
        error1.setMessage("Member not found");
        error1.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        memberResponse1.setError(error1);

        // Set values for memberResponse2
        memberResponse2.setId(1L);
        memberResponse2.setFirstName("John");
        memberResponse2.setLastName("Doe");
        memberResponse2.setEmail("john.doe@example.com");

        ErrorDTO error2 = new ErrorDTO();
        error2.setCode("MEMBER_NOT_FOUND");
        error2.setMessage("Member not found");
        error2.setTimestamp(error1.getTimestamp());
        memberResponse2.setError(error2);

        assertEquals(memberResponse1, memberResponse2);

        assertEquals(memberResponse1.hashCode(), memberResponse2.hashCode());

        assertEquals(1L, memberResponse1.getId());
        assertEquals("John", memberResponse1.getFirstName());
        assertEquals("Doe", memberResponse1.getLastName());
        assertEquals("john.doe@example.com", memberResponse1.getEmail());
        assertEquals(error1, memberResponse1.getError());

        memberResponse1.setFirstName("Jane");
        assertEquals("Jane", memberResponse1.getFirstName());
    }
}