package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.entity.Member;
import com.capgemini.library.management.project.library_management.model.MemberDTO;
import com.capgemini.library.management.project.library_management.repository.MemberRepository;
import com.capgemini.library.management.project.library_management.service.MemberAllocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberAllocationServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MemberAllocationServiceImpl memberAllocationService;

    private Member member;
    private MemberDTO memberDTO;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);
        member.setFirstName("John");
        member.setLastName("Doe");
        member.setEmail("john.doe@example.com");
        member.setMembershipDate(LocalDate.of(2023, 1, 1));
        member.setStatus(Member.MemberStatus.ACTIVE);

        memberDTO = new MemberDTO();
        memberDTO.setId(1L);
        memberDTO.setFirstName("John");
        memberDTO.setLastName("Doe");
        memberDTO.setEmail("john.doe@example.com");
        memberDTO.setMembershipDate(LocalDate.of(2023, 1, 1));
        memberDTO.setStatus(MemberDTO.StatusEnum.ACTIVE);
    }

    @Test
    void testRegisterMember() {
        when(modelMapper.map(memberDTO, Member.class)).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(member);
        when(modelMapper.map(member, MemberDTO.class)).thenReturn(memberDTO);

        MemberDTO result = memberAllocationService.registerMember(memberDTO);

        assertNotNull(result);
        assertEquals(memberDTO.getId(), result.getId());
        assertEquals(memberDTO.getFirstName(), result.getFirstName());
        assertEquals(memberDTO.getLastName(), result.getLastName());
        assertEquals(memberDTO.getEmail(), result.getEmail());
        assertEquals(memberDTO.getMembershipDate(), result.getMembershipDate());
        assertEquals(memberDTO.getStatus(), result.getStatus());

        verify(memberRepository, times(1)).save(member);
        verify(modelMapper, times(1)).map(memberDTO, Member.class);
        verify(modelMapper, times(1)).map(member, MemberDTO.class);
    }

    @Test
    void testGetAllMembersWithStatus() {
        Member.MemberStatus status = Member.MemberStatus.ACTIVE;
        when(memberRepository.findByStatus(status)).thenReturn(Arrays.asList(member));
        when(modelMapper.map(member, MemberDTO.class)).thenReturn(memberDTO);

        List<MemberDTO> result = memberAllocationService.getAllMembers(status.name());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(memberDTO.getId(), result.get(0).getId());
        assertEquals(memberDTO.getFirstName(), result.get(0).getFirstName());
        assertEquals(memberDTO.getLastName(), result.get(0).getLastName());
        assertEquals(memberDTO.getEmail(), result.get(0).getEmail());
        assertEquals(memberDTO.getMembershipDate(), result.get(0).getMembershipDate());
        assertEquals(memberDTO.getStatus(), result.get(0).getStatus());

        verify(memberRepository, times(1)).findByStatus(status);
        verify(modelMapper, times(1)).map(member, MemberDTO.class);
    }

    @Test
    void testGetAllMembersWithoutStatus() {
        when(memberRepository.findAll()).thenReturn(Arrays.asList(member));
        when(modelMapper.map(member, MemberDTO.class)).thenReturn(memberDTO);

        List<MemberDTO> result = memberAllocationService.getAllMembers(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(memberDTO.getId(), result.get(0).getId());
        assertEquals(memberDTO.getFirstName(), result.get(0).getFirstName());
        assertEquals(memberDTO.getLastName(), result.get(0).getLastName());
        assertEquals(memberDTO.getEmail(), result.get(0).getEmail());
        assertEquals(memberDTO.getMembershipDate(), result.get(0).getMembershipDate());
        assertEquals(memberDTO.getStatus(), result.get(0).getStatus());

        verify(memberRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(member, MemberDTO.class);
    }
}